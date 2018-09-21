/**
 * Copyright (c) 2000-2018 Liferay, Inc. All rights reserved.
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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liferay.faces.portal.component.inputrichtext.internal;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.exportimport.kernel.lar.StagedModelType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.util.OrderByComparator;

import com.liferay.trash.kernel.model.TrashEntry;

import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;


/**
 * @author  kyle
 */
public class WikiPageStringImpl implements WikiPage {

	// serialVersionUID
	private static final long serialVersionUID = 74596638262890750L;

	// Private Final Data Members
	private final String string;

	public WikiPageStringImpl(String string) {
		this.string = string;
	}

	@Override
	public Folder addAttachmentsFolder() throws PortalException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object clone() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int compareTo(WikiPage wp) {
		throw new UnsupportedOperationException();
	}

	@Override
	public WikiPage fetchParentPage() {
		throw new UnsupportedOperationException();
	}

	@Override
	public WikiPage fetchRedirectPage() {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<FileEntry> getAttachmentsFileEntries() throws PortalException {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<FileEntry> getAttachmentsFileEntries(int i, int i1) throws PortalException {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<FileEntry> getAttachmentsFileEntries(int i, int i1, OrderByComparator obc) throws PortalException {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<FileEntry> getAttachmentsFileEntries(String[] strings, int i, int i1, OrderByComparator<FileEntry> obc)
		throws PortalException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getAttachmentsFileEntriesCount() throws PortalException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getAttachmentsFileEntriesCount(String[] strings) throws PortalException {
		throw new UnsupportedOperationException();
	}

	@Override
	public long getAttachmentsFolderId() {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<WikiPage> getChildPages() {
		throw new UnsupportedOperationException();
	}

	@Override
	public long getCompanyId() {
		throw new UnsupportedOperationException();
	}

	@Override
	public long getContainerModelId() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getContainerModelName() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getContent() {
		return string;
	}

	@Override
	public Date getCreateDate() {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<FileEntry> getDeletedAttachmentsFileEntries() throws PortalException {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<FileEntry> getDeletedAttachmentsFileEntries(int i, int i1) throws PortalException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getDeletedAttachmentsFileEntriesCount() throws PortalException {
		throw new UnsupportedOperationException();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getFormat() {
		throw new UnsupportedOperationException();
	}

	@Override
	public long getGroupId() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean getHead() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Date getLastPublishDate() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean getMinorEdit() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Class<?> getModelClass() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getModelClassName() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Date getModifiedDate() {
		throw new UnsupportedOperationException();
	}

	@Override
	public WikiNode getNode() {
		throw new UnsupportedOperationException();
	}

	@Override
	public long getNodeAttachmentsFolderId() {
		throw new UnsupportedOperationException();
	}

	@Override
	public long getNodeId() {
		throw new UnsupportedOperationException();
	}

	@Override
	public long getPageId() {
		throw new UnsupportedOperationException();
	}

	@Override
	public long getParentContainerModelId() {
		throw new UnsupportedOperationException();
	}

	@Override
	public WikiPage getParentPage() throws PortalException {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<WikiPage> getParentPages() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getParentTitle() {
		throw new UnsupportedOperationException();
	}

	@Override
	public long getPrimaryKey() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		throw new UnsupportedOperationException();
	}

	@Override
	public WikiPage getRedirectPage() throws PortalException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getRedirectTitle() {
		throw new UnsupportedOperationException();
	}

	@Override
	public long getResourcePrimKey() {
		throw new UnsupportedOperationException();
	}

	@Override
	public StagedModelType getStagedModelType() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getStatus() {
		throw new UnsupportedOperationException();
	}

	@Override
	public long getStatusByUserId() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getStatusByUserName() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getStatusByUserUuid() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Date getStatusDate() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getSummary() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getTitle() {
		throw new UnsupportedOperationException();
	}

	@Override
	public TrashEntry getTrashEntry() throws PortalException {
		throw new UnsupportedOperationException();
	}

	@Override
	public long getTrashEntryClassPK() {
		throw new UnsupportedOperationException();
	}

	@Override
	public TrashHandler getTrashHandler() {
		throw new UnsupportedOperationException();
	}

	@Override
	public long getUserId() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getUserName() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getUserUuid() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getUuid() {
		throw new UnsupportedOperationException();
	}

	@Override
	public double getVersion() {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<WikiPage> getViewableChildPages() {
		throw new UnsupportedOperationException();
	}

	@Override
	public WikiPage getViewableParentPage() {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<WikiPage> getViewableParentPages() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isApproved() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isCachedModel() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isDenied() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isDraft() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isEntityCacheEnabled() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isEscapedModel() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isExpired() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isHead() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isInactive() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isIncomplete() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isInTrash() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isInTrashContainer() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isInTrashExplicitly() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isInTrashImplicitly() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isMinorEdit() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isNew() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isPending() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isResourceMain() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isScheduled() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void persist() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void resetOriginalValues() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setAttachmentsFolderId(long l) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setCachedModel(boolean bln) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setCompanyId(long l) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setContainerModelId(long l) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setContent(String string) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setCreateDate(Date date) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setExpandoBridgeAttributes(BaseModel<?> bm) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge eb) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext sc) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setFormat(String string) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setGroupId(long l) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setHead(boolean bln) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setLastPublishDate(Date date) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setMinorEdit(boolean bln) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setModelAttributes(Map<String, Object> arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setModifiedDate(Date date) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setNew(boolean bln) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setNodeId(long l) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setPageId(long l) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setParentContainerModelId(long l) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setParentTitle(String string) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setPrimaryKey(long l) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setPrimaryKeyObj(Serializable srlzbl) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setRedirectTitle(String string) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setResourcePrimKey(long l) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setStatus(int i) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setStatusByUserId(long l) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setStatusByUserName(String string) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setStatusByUserUuid(String string) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setStatusDate(Date date) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setSummary(String string) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setTitle(String string) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setUserId(long l) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setUserName(String string) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setUserUuid(String string) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setUuid(String string) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setVersion(double d) {
		throw new UnsupportedOperationException();
	}

	@Override
	public CacheModel<WikiPage> toCacheModel() {
		throw new UnsupportedOperationException();
	}

	@Override
	public WikiPage toEscapedModel() {
		throw new UnsupportedOperationException();
	}

	@Override
	public WikiPage toUnescapedModel() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toXmlString() {
		throw new UnsupportedOperationException();
	}
}
