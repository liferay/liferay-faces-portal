/**
 * Copyright (c) 2000-2021 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
package com.liferay.faces.demos.list;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.component.datatable.DataTable;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

import com.liferay.portal.kernel.exception.NoSuchUserException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;


/**
 * This class extends the PrimeFaces {@link LazyDataModel} in order to provide a lazy-loaded list of {@link User}
 * objects to the p:dataTable in the users.xhtml Facelet view.
 *
 * @author  Neil Griffin
 * @author  Kyle Stiemann
 */
public class UserLazyDataModel extends LazyDataModel<User> implements Serializable {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(UserLazyDataModel.class);

	// serialVersionUID
	private static final long serialVersionUID = 2087063071907939066L;

	// Private Constants
	private static final String DEFAULT_SORT_CRITERIA = "lastName";

	// Private Data Members
	private long companyId;
	private UserLocalService userLocalService;

	public UserLazyDataModel(UserLocalService userLocalService, long companyId, int pageSize) {

		this.companyId = companyId;
		this.userLocalService = userLocalService;

		setPageSize(pageSize);
		setRowCount(countRows());
	}

	public int countRows() {
		return countRows(null, null, null, null, null);
	}

	public int countRows(String firstNameFilter, String middleNameFilter, String lastNameFilter,
		String screenNameFilter, String emailAddressFilter) {

		int totalCount = 0;

		if (userLocalService != null) {

			try {

				logger.debug("countRows: userLocalService.getUsersCount() = " + userLocalService.getUsersCount());

				LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
				params.put("expandoAttributes", null);

				boolean andSearch = true;
				int status = WorkflowConstants.STATUS_ANY;
				totalCount = userLocalService.searchCount(companyId, firstNameFilter, middleNameFilter, lastNameFilter,
						screenNameFilter, emailAddressFilter, status, params, andSearch);
			}
			catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}

		return totalCount;
	}

	@Override
	public User getRowData(String rowKey) {
		User user = null;

		try {
			user = userLocalService.getUserById(Long.parseLong(rowKey));
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return user;
	}

	@Override
	public Object getRowKey(User user) {
		return user.getUserId();
	}

	/**
	 * This method is called by the PrimeFaces {@link DataTable} according to the rows specified in the currently
	 * displayed page of data.
	 *
	 * @param  first      The zero-relative first row index.
	 * @param  pageSize   The number of rows to fetch.
	 * @param  sortField  The name of the field which the table is sorted by.
	 * @param  sortOrder  The sort order, which can be either ascending (default) or descending.
	 * @param  filters    The query criteria.
	 */
	@Override
	public List<User> load(int first, int pageSize, String sortField, SortOrder sortOrder,
		Map<String, FilterMeta> filters) {

		List<User> users = null;
		int rowCount = 0;

		Sort sort;

		// sort
		if (sortField != null) {

			if (sortOrder.equals(SortOrder.DESCENDING)) {
				sort = SortFactoryUtil.getSort(User.class, sortField, "desc");
			}
			else {
				sort = SortFactoryUtil.getSort(User.class, sortField, "asc");
			}
		}
		else {
			sort = SortFactoryUtil.getSort(User.class, DEFAULT_SORT_CRITERIA, "asc");
		}

		try {
			LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
			int nonInclusiveFinishRow = first + pageSize;

			boolean andSearch = true;
			int status = WorkflowConstants.STATUS_ANY;

			String firstNameFilter = trimExpresssion(filters.get("firstName"));
			String middleNameFilter = trimExpresssion(filters.get("middleName"));
			String lastNameFilter = trimExpresssion(filters.get("lastName"));
			String screenNameFilter = trimExpresssion(filters.get("screenName"));
			String emailAddressFilter = trimExpresssion(filters.get("emailAddress"));

			// For the sake of speed, search for users in the index rather than
			// querying the database directly.

			Hits hits = userLocalService.search(companyId, firstNameFilter, middleNameFilter, lastNameFilter,
					screenNameFilter, emailAddressFilter, status, params, andSearch, first, nonInclusiveFinishRow,
					sort);

			List<Document> documentHits = hits.toList();

			logger.debug(
				"filters firstNameFilter=[{0}] middleNameFilter=[{1}] lastNameFilter=[{2}] screenNameFilter=[{3}] emailAddressFilter=[{4}] active=[{5}] andSearch=[{6}] startRow=[{7}] nonInclusiveFinishRow=[{8}] sortColumn=[{9}] reverseOrder=[{10}] hitCount=[{11}]",
				firstNameFilter, middleNameFilter, lastNameFilter, screenNameFilter, emailAddressFilter, status,
				andSearch, first, nonInclusiveFinishRow, sortField, sort.isReverse(), documentHits.size());

			// Convert the results from the search index into a list of user
			// objects.
			users = new ArrayList<User>(documentHits.size());

			for (Document document : documentHits) {

				long userId = GetterUtil.getLong(document.get(Field.USER_ID));

				try {
					User user = userLocalService.getUserById(userId);
					users.add(user);
				}
				catch (NoSuchUserException nsue) {
					logger.error("User with userId=[{0}] does not exist in the search index. Please reindex.");
				}
			}

			rowCount = countRows(firstNameFilter, middleNameFilter, lastNameFilter, screenNameFilter,
					emailAddressFilter);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		setRowCount(rowCount);

		return users;

	}

	protected String trimExpresssion(FilterMeta filterMeta) {

		String expression = null;

		if (filterMeta != null) {
			String value = "";
			Object filterValue = filterMeta.getFilterValue();

			if (filterValue != null) {
				value = filterValue.toString();
			}

			String trimmedValue = value.trim();

			if (trimmedValue.length() > 0) {
				expression = trimmedValue.toLowerCase();
			}
		}

		return expression;
	}
}
