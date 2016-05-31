/*******************************************************************************
 * Copyright (C) 2016 Black Duck Software, Inc.
 * http://www.blackducksoftware.com/
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 *  with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 *  under the License.
 *
 *******************************************************************************/
package com.blackducksoftware.tools.commonframework.standard.protex;

import com.blackducksoftware.tools.commonframework.standard.common.ProjectPojo;

/**
 * The Class ProtexProjectPojo.
 */
public class ProtexProjectPojo extends ProjectPojo {

    /** The project key. */
    private String projectKey;

    /** The project name. */
    private String projectName;

    /** The date. */
    private String date;

    private String analyzedDate;

    /** The download path. */
    private String downloadPath;

    /** The view path. */
    private String viewPath;

    /** The status. */
    private String status;

    /** The uploaded mbe. */
    private String uploadedMBE;

    /** The uploaded app a. */
    private String uploadedAppA;

    // ** The report location path */
    private String reportPath;

    /**
     * Instantiates a new protex project pojo.
     */
    public ProtexProjectPojo() {
    }

    /**
     * Instantiates a new protex project pojo.
     *
     * @param projectKey
     *            the project key
     * @param projectName
     *            the project name
     */
    public ProtexProjectPojo(String projectKey, String projectName) {
	this.projectKey = projectKey;
	this.projectName = projectName;
    }

    @Override
    public String getProjectKey() {
	return projectKey;
    }

    /**
     * Sets the project key.
     *
     * @param projectKey
     *            the new project key
     */
    public void setProjectKey(String projectKey) {
	this.projectKey = projectKey;
    }

    @Override
    public String getProjectName() {
	return projectName;
    }

    /**
     * Sets the project name.
     *
     * @param projectName
     *            the new project name
     */
    public void setProjectName(String projectName) {
	this.projectName = projectName;
    }

    /**
     * Gets the date.
     *
     * @return the date
     */
    public String getDate() {

	return date;
    }

    /**
     * Sets the date.
     *
     * @param date
     *            the new date
     */
    public void setDate(String date) {
	this.date = date;
    }

    /**
     * Gets the download path.
     *
     * @return the download path
     */
    public String getDownloadPath() {
	return downloadPath;
    }

    /**
     * Sets the download path.
     *
     * @param downloadPath
     *            the new download path
     */
    public void setDownloadPath(String downloadPath) {
	this.downloadPath = downloadPath;
    }

    /**
     * Gets the status.
     *
     * @return the status
     */
    public String getStatus() {
	return status;
    }

    /**
     * Sets the status.
     *
     * @param status
     *            the new status
     */
    public void setStatus(String status) {
	this.status = status;
    }

    /**
     * Gets the uploaded mbe.
     *
     * @return the uploaded mbe
     */
    public String getUploadedMBE() {
	return uploadedMBE;
    }

    /**
     * Sets the uploaded mbe.
     *
     * @param uploadedMBE
     *            the new uploaded mbe
     */
    public void setUploadedMBE(String uploadedMBE) {
	this.uploadedMBE = uploadedMBE;
    }

    /**
     * Gets the uploaded app a.
     *
     * @return the uploaded app a
     */
    public String getUploadedAppA() {
	return uploadedAppA;
    }

    /**
     * Sets the uploaded app a.
     *
     * @param uploadedAppA
     *            the new uploaded app a
     */
    public void setUploadedAppA(String uploadedAppA) {
	this.uploadedAppA = uploadedAppA;
    }

    /**
     * Gets the view path.
     *
     * @return the view path
     */
    public String getViewPath() {
	return viewPath;
    }

    /**
     * Sets the view path.
     *
     * @param viewPath
     *            the new view path
     */
    public void setViewPath(String viewPath) {
	this.viewPath = viewPath;
    }

    public void setAnalyzedDate(String date) {
	analyzedDate = date;
    }

    /**
     * Return the report location path
     *
     * @return the report location path
     */
    public String getReportPath() {
	return reportPath;
    }

    /**
     * Set the report location path
     *
     * @param reportPath
     *            the report location path
     */
    public void setReportPath(String reportPath) {
	this.reportPath = reportPath;
    }

    @Override
    public String getAnalyzedDate() {
	return analyzedDate;
    }

}
