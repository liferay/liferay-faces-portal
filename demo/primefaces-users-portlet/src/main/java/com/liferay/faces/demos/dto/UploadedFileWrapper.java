/**
 * Copyright (c) 2000-2022 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.demos.dto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.faces.FacesWrapper;

import com.liferay.faces.demos.util.UploadedFileUtil;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.model.UploadedFile;


/**
 * This class provides a convenient mechanism for converting a PrimeFaces org.primefaces.model.UploadedFile object to an
 * instance of the {@link UploadedFile} interface provided by the Liferay Faces Bridge implementation.
 *
 * @author  Neil Griffin
 * @author  Kyle Stiemann
 */
public class UploadedFileWrapper implements Serializable, UploadedFile,
	FacesWrapper<org.primefaces.model.file.UploadedFile> {

	// serialVersionUID
	private static final long serialVersionUID = 1078847948835811331L;

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(UploadedFileWrapper.class);

	// Private Data Members
	private Map<String, Object> attributeMap;
	private String id;
	private File renamedFile;
	private Status status;
	private org.primefaces.model.file.UploadedFile wrappedUploadedFile;

	public UploadedFileWrapper(org.primefaces.model.file.UploadedFile uploadedFile, UploadedFile.Status status,
		String uniqueFolderName) {
		this.wrappedUploadedFile = uploadedFile;
		this.status = status;
		this.attributeMap = new HashMap<String, Object>();
		this.id = Long.toString(((long) hashCode()) + System.currentTimeMillis());

		File primeFacesFile = getFile(uniqueFolderName);
		String parent = primeFacesFile.getParent();

		// Rename the file to a predictable filename so that the
		// UserPortraitResource class will be able to find the
		// uploaded file in the temporary folder.
		String newFileName = UploadedFileUtil.createFileName(this.id);
		this.renamedFile = new File(parent, newFileName);
		primeFacesFile.renameTo(this.renamedFile);
	}

	public void delete() throws IOException {

		if (renamedFile != null) {
			renamedFile.delete();
		}
	}

	public String getAbsolutePath() {
		String absolutePath = null;

		if (renamedFile != null) {
			absolutePath = renamedFile.getAbsolutePath();
		}

		return absolutePath;
	}

	public Map<String, Object> getAttributes() {
		return attributeMap;
	}

	public byte[] getBytes() throws IOException {

		byte[] bytes = null;

		if ((renamedFile != null) && (renamedFile.exists())) {
			RandomAccessFile randomAccessFile = new RandomAccessFile(renamedFile, "r");
			bytes = new byte[(int) randomAccessFile.length()];
			randomAccessFile.readFully(bytes);
			randomAccessFile.close();
		}

		return bytes;
	}

	public String getCharSet() {
		throw new UnsupportedOperationException();
	}

	public String getContentType() {
		return wrappedUploadedFile.getContentType();
	}

	public String getHeader(String name) {
		throw new UnsupportedOperationException();
	}

	public Collection<String> getHeaderNames() {
		throw new UnsupportedOperationException();
	}

	public Collection<String> getHeaders(String name) {
		throw new UnsupportedOperationException();
	}

	public String getId() {
		return id;
	}

	public InputStream getInputStream() throws IOException {
		return new FileInputStream(renamedFile);
	}

	public String getMessage() {
		throw new UnsupportedOperationException();
	}

	public String getName() {
		return wrappedUploadedFile.getFileName();
	}

	public long getSize() {
		return wrappedUploadedFile.getSize();
	}

	public Status getStatus() {
		return status;
	}

	public org.primefaces.model.file.UploadedFile getWrapped() {
		return wrappedUploadedFile;
	}

	public void write(String fileName) throws IOException {
		OutputStream outputStream = new FileOutputStream(fileName);
		outputStream.write(getBytes());
		outputStream.close();
	}

	/**
	 * Since the PrimeFaces UploadedFile interface does not provide a method for deleting the file, Liferay Faces Bridge
	 * automatically deletes it when the wrappedUploadedFile.getContents() method is called.
	 */
	protected File getFile(String uniqueFolderName) {

		File file = null;

		try {
			File tempFolder = new File(UploadedFileUtil.getTempDir()); // "java.io.tmpdir"
			File uniqueFolder = new File(tempFolder, uniqueFolderName);

			if (!uniqueFolder.exists()) {
				uniqueFolder.mkdirs();
			}

			String fileNamePrefix = "uploadedFile" + getId();
			String fileNameSuffix = ".dat";
			file = File.createTempFile(fileNamePrefix, fileNameSuffix, uniqueFolder);

			OutputStream outputStream = new FileOutputStream(file);
			outputStream.write(wrappedUploadedFile.getContent());
			outputStream.close();

			// Temporary file maintained by PrimeFaces is automatically deleted.
			// See JavaDoc comments above.
		}
		catch (Exception e) {
			logger.error(e);
		}

		return file;
	}
}
