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
package com.blackducksoftware.tools.commonframework.standard.codecenter.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blackducksoftware.tools.commonframework.standard.codecenter.pojo.ApplicationPojo;
import com.blackducksoftware.tools.commonframework.standard.codecenter.pojo.ApplicationPojoImpl;
import com.blackducksoftware.tools.commonframework.standard.codecenter.pojo.ComponentPojo;
import com.blackducksoftware.tools.commonframework.standard.codecenter.pojo.ComponentPojoImpl;
import com.blackducksoftware.tools.commonframework.standard.codecenter.pojo.ComponentUsePojo;
import com.blackducksoftware.tools.commonframework.standard.codecenter.pojo.VulnerabilityMapping;
import com.blackducksoftware.tools.commonframework.standard.codecenter.pojo.VulnerabilityMappingImpl;
import com.blackducksoftware.tools.commonframework.standard.codecenter.pojo.VulnerabilityPojo;

/**
 * Code Center Data Access Object for fields that can only be accessed by going
 * direct to the database.
 *
 * @author Steve Billings
 * @date Oct 29, 2014
 *
 */
@Deprecated
public class CodeCenter6_6_1DbDao {
    private final Connection connBdsVuln;

    private final Connection connBdsCatalog;
    private final QueryBuilder queryBuilder = new QueryBuilder();
    private final Map<Long, String> vulnStatusCache;

    private final Logger log = LoggerFactory.getLogger(this.getClass()
	    .getName());

    public CodeCenter6_6_1DbDao(CodeCenterDaoConfigManager config)
	    throws SQLException {
	connBdsVuln = getDbConnection(config, "bds_vuln");
	connBdsCatalog = getDbConnection(config, "bds_catalog");
	vulnStatusCache = new HashMap<Long, String>(16);
    }

    private Connection getDbConnection(CodeCenterDaoConfigManager config,
	    String databaseName) throws SQLException {
	log.debug("Opening database connection to " + databaseName);
	String url = "jdbc:postgresql://" + config.getCcDbServerName() + ":"
		+ config.getCcDbPort() + "/" + databaseName;
	Properties props = new Properties();
	props.setProperty("user", config.getCcDbUserName());
	props.setProperty("password", config.getCcDbPassword());
	Connection conn = DriverManager.getConnection(url, props);
	return conn;
    }

    /**
     * Use this to set on a new vulnerability all fields fetched FROM DB
     *
     * @param vuln
     * @param compUse
     * @throws SQLException
     */
    public void setDbFields(VulnerabilityPojo vuln, ComponentUsePojo compUse)
	    throws SQLException {
	setDefaults(vuln);
	addStatus(vuln, compUse);
	addRemediationDates(compUse, vuln);
    }

    /**
     * Use this to update on a re-used vulnerability only the fields that are
     * use-specific.
     *
     * @param vuln
     * @param compUse
     * @throws SQLException
     */
    public void setVulnStatusFields(VulnerabilityPojo vuln,
	    ComponentUsePojo compUse) throws SQLException {
	setDefaults(vuln);
	addStatus(vuln, compUse);
    }

    public void close() throws SQLException {
	if (connBdsVuln != null) {
	    log.debug("Closing bds_vuln DB connection");
	    connBdsVuln.close();
	}

	if (connBdsCatalog != null) {
	    log.debug("Closing bds_catalog DB connection");
	    connBdsCatalog.close();
	}
    }

    private void setDefaults(VulnerabilityPojo vuln) {
	vuln.setStatus("");
	vuln.setStatusComment("");
    }

    private void addStatus(VulnerabilityPojo vuln, ComponentUsePojo compUse)
	    throws SQLException {
	long vulnStatusId = setStatusComment(vuln, compUse);
	if (vulnStatusId != -1) {
	    vuln.setStatusId(vulnStatusId);
	    String vulnStatusString = getStatusString(vulnStatusId);
	    vuln.setStatus(vulnStatusString);
	}
    }

    private String getStatusString(long vulnStatusId) throws SQLException {
	String vulnStatusString = "";
	Long key = vulnStatusId;

	if (vulnStatusCache.containsKey(key)) {
	    vulnStatusString = vulnStatusCache.get(key);
	    return vulnStatusString;
	}

	log.debug("Fetching vulnerability status name from vulnerability_status table for vulnStatusId: "
		+ vulnStatusId);
	Statement stmt = connBdsCatalog.createStatement();
	String sql = "SELECT name FROM vulnerability_status WHERE id = "
		+ vulnStatusId;
	ResultSet rs = stmt.executeQuery(sql);
	if (rs.next()) {
	    vulnStatusString = rs.getString("name");
	}

	vulnStatusCache.put(key, vulnStatusString);
	return vulnStatusString;
    }

    private long setStatusComment(VulnerabilityPojo vuln,
	    ComponentUsePojo compUse) throws SQLException {
	log.debug("Fetching vulnerability_status_id, comment from componentuser_vulnerability table for vuln ID: "
		+ vuln.getId() + " / compUse ID: " + compUse.getId());
	long vulnStatusId = -1;
	Statement stmt = connBdsCatalog.createStatement();
	String sql = "SELECT vulnerability_status_id,comment FROM componentuse_vulnerability "
		+ "WHERE vulnerability_id = "
		+ vuln.getId()
		+ " "
		+ "AND componentuse_id = '" + compUse.getId() + "'";
	ResultSet rs = stmt.executeQuery(sql);
	if (rs.next()) {
	    vulnStatusId = rs.getLong("vulnerability_status_id");
	    String vulnStatusComment = rs.getString("comment");
	    vuln.setStatusComment(vulnStatusComment);
	}
	return vulnStatusId;
    }

    private void addRemediationDates(ComponentUsePojo compUse,
	    VulnerabilityPojo vuln) throws SQLException {

	vuln.setTargetRemediationDate(null);
	vuln.setActualRemediationDate(null);
	RemediationDates dates = getRemediationDates(vuln.getId(),
		compUse.getId());

	if (dates != null) {
	    vuln.setTargetRemediationDate(dates.getTargetRemediationDate());
	    vuln.setActualRemediationDate(dates.getActualRemediationDate());
	}
    }

    /**
     * Get remediation dates. Can use this to check to see whether the
     * componentuse_vulnerabilty record exists yet.
     *
     * @param vulnId
     * @param compUseId
     * @return
     * @throws SQLException
     */
    private RemediationDates getRemediationDates(String vulnId, String compUseId)
	    throws SQLException {
	log.debug("Fetching date_remediation, date_completion from componentuse_vulnerability table for vulnId: "
		+ vulnId + " / compUseId: " + compUseId);
	RemediationDates dates = null;

	Statement stmt = connBdsCatalog.createStatement();
	String sql = "SELECT date_remediation,date_completion FROM componentuse_vulnerability "
		+ "WHERE vulnerability_id = "
		+ vulnId
		+ " "
		+ "AND componentuse_id = '" + compUseId + "'";

	try {
	    ResultSet rs = stmt.executeQuery(sql);
	    if (rs.next()) {
		Date targetRemediationDate = rs.getDate("date_remediation");
		Date actualRemediationDate = rs.getDate("date_completion");
		dates = new RemediationDates(targetRemediationDate,
			actualRemediationDate);
		log.debug("From DB: target remediation date: "
			+ targetRemediationDate + " ("
			+ getTimeMillis(targetRemediationDate) + ")"
			+ "; actual remediation date: " + actualRemediationDate
			+ " (" + getTimeMillis(actualRemediationDate) + ")");
	    }
	} catch (SQLException e) {
	    log.debug("Error executing SQL: " + sql);
	    log.warn("Unable to read componentuse_vulnerability remediation dates, which were added in Code Center 6.7.1p2; "
		    + " This is normal when running against older Code Center servers.");
	}
	return dates;
    }

    private long getTimeMillis(Date date) {
	if (date == null) {
	    return 0L;
	}
	return date.getTime();
    }

    /**
     * Update the componentuse_vulnerability data: vulnerability status, staus
     * comment, and remediation dates. Update target remediation date on
     * compuse_vulnerability.
     *
     * @param compUse
     * @param vuln
     * @throws Exception
     */
    public void updateCompUseVulnData(ComponentUsePojo compUse,
	    VulnerabilityPojo vuln) throws Exception {

	if (getRemediationDates(vuln.getId(), compUse.getId()) != null) {
	    updateCompUseVulnDataViaUpdate(compUse, vuln);
	} else {
	    updateCompUseVulnDataViaInsert(compUse, vuln);
	}
    }

    // Tested, works 10/30 10:21 pm
    private void updateCompUseVulnDataViaUpdate(ComponentUsePojo compUse,
	    VulnerabilityPojo vuln) throws Exception {
	log.debug("Inserting component use vulnerability data");
	StringBuilder assignments = new StringBuilder();

	int assignmentCount = 0;
	if (vuln.getStatusId() > 0L) {
	    assignments.append("vulnerability_status_id=");
	    assignments.append(vuln.getStatusId());
	    assignmentCount++;
	}

	if ((vuln.getStatusComment() != null)
		&& (vuln.getStatusComment().length() > 0)) {
	    if (assignments.length() > 0) {
		assignments.append(",");
	    }
	    assignments.append("comment='");
	    assignments.append(StringEscapeUtils.escapeSql(vuln
		    .getStatusComment()));
	    assignments.append("'");
	    assignmentCount++;
	}
	if (vuln.getTargetRemediationDate() != null) {
	    java.sql.Date targetRemediationDateSql = new java.sql.Date(vuln
		    .getTargetRemediationDate().getTime());
	    if (assignments.length() > 0) {
		assignments.append(",");
	    }
	    assignments.append("date_remediation='");
	    assignments.append(targetRemediationDateSql);
	    assignments.append("'");
	    assignmentCount++;
	    log.debug("Setting target remediation date to: "
		    + targetRemediationDateSql + " ("
		    + targetRemediationDateSql.getTime() + ")");
	}
	if (vuln.getActualRemediationDate() != null) {
	    java.sql.Date actualRemediationDateSql = new java.sql.Date(vuln
		    .getActualRemediationDate().getTime());
	    if (assignments.length() > 0) {
		assignments.append(",");
	    }
	    assignments.append("date_completion='");
	    assignments.append(actualRemediationDateSql);
	    assignments.append("'");
	    assignmentCount++;
	    log.debug("Setting actual remediation date to: "
		    + actualRemediationDateSql + " ("
		    + actualRemediationDateSql.getTime() + ")");
	}
	if (assignmentCount == 0) {
	    return;
	}
	String sql = "UPDATE componentuse_vulnerability SET " + assignments
		+ " WHERE vulnerability_id=" + vuln.getId() + " "
		+ "AND componentuse_id='" + compUse.getId() + "'";

	Statement stmt = connBdsCatalog.createStatement();
	try {
	    log.debug("Executing sql: " + sql);
	    stmt.executeUpdate(sql);
	} catch (SQLException e) {
	    log.error("Error executing SQL: " + sql);
	    String highLevelMsg = "Unable to set componentuse_vulnerability remediation dates, which were added in Code Center 6.7.1p2";
	    throw new Exception(highLevelMsg + ": " + e.getMessage());
	}
    }

    private long getNextDatabaseRowId() throws Exception {

	Statement stmt = connBdsCatalog.createStatement();
	String sql = "SELECT nextval( 'hibernate_sequence' )";

	String exceptionMsg = "Empty result set";
	try {
	    ResultSet rs = stmt.executeQuery(sql);
	    if (rs.next()) {
		long id = rs.getLong(1);
		return id;
	    }
	} catch (SQLException e) {
	    log.error("Error executing SQL: " + sql);
	    exceptionMsg = e.getMessage();
	}
	throw new Exception(
		"Error deriving next datanase record id by reading hibernate_sequence: "
			+ exceptionMsg);
    }

    // Tested, works 10/30 10:21 pm
    private void updateCompUseVulnDataViaInsert(ComponentUsePojo compUse,
	    VulnerabilityPojo vuln) throws Exception {
	log.debug("Updating component use vulnerability data");

	Statement stmt = connBdsCatalog.createStatement();
	long databaseRowId = getNextDatabaseRowId();

	boolean valueToInsert = false;

	StringBuilder columnList = new StringBuilder();
	StringBuilder valuesList = new StringBuilder();

	columnList.append("id");
	valuesList.append(databaseRowId);
	if (vuln.getStatusId() > 0) {
	    columnList.append(",vulnerability_status_id");
	    valuesList.append("," + vuln.getStatusId());
	}
	columnList.append(",componentuse_id,vulnerability_id");
	valuesList.append(",'");
	valuesList.append(compUse.getId());
	valuesList.append("'");
	valuesList.append(",");
	valuesList.append(vuln.getId());

	if ((vuln.getStatusComment() != null)
		&& (vuln.getStatusComment().length() > 0)) {
	    columnList.append(",comment");
	    valuesList.append(",'");
	    valuesList.append(StringEscapeUtils.escapeSql(vuln
		    .getStatusComment()));
	    valuesList.append("'");
	    valueToInsert = true;
	}
	if (vuln.getTargetRemediationDate() != null) {
	    java.sql.Date targetRemediationDateSql = new java.sql.Date(vuln
		    .getTargetRemediationDate().getTime());
	    columnList.append(",date_remediation");
	    valuesList.append(",'");
	    valuesList.append(targetRemediationDateSql);
	    valuesList.append("'");
	    valueToInsert = true;
	    log.debug("Setting target remediation date to: "
		    + targetRemediationDateSql + " ("
		    + targetRemediationDateSql.getTime() + ")");
	}
	if (vuln.getActualRemediationDate() != null) {
	    java.sql.Date actualRemediationDateSql = new java.sql.Date(vuln
		    .getActualRemediationDate().getTime());
	    columnList.append(",date_completion");
	    valuesList.append(",'");
	    valuesList.append(actualRemediationDateSql);
	    valuesList.append("'");
	    valueToInsert = true;
	    log.debug("Setting actual remediation date to: "
		    + actualRemediationDateSql + " ("
		    + actualRemediationDateSql.getTime() + ")");
	}

	if (!valueToInsert) {
	    return;
	}

	String sql = "INSERT INTO componentuse_vulnerability (" + columnList
		+ ") VALUES (" + valuesList + ")";

	try {
	    log.debug("Executing sql: " + sql);
	    stmt.execute(sql);
	} catch (SQLException e) {
	    log.error("Error executing SQL: " + sql);
	    String highLevelMsg = "Unable to set componentuse_vulnerability remediation dates, which were added in Code Center 6.7.1p2";
	    throw new Exception(highLevelMsg + ": " + e.getMessage());
	}
    }

    /**
     *
     * @param timeInterval
     * @param date
     * @return
     * @throws SQLException
     *             if a database access error occurs or this method is called on
     *             a closed result set
     */
    public List<VulnerabilityMapping> retrieveVulnerabilityImpact(
	    int timeInterval, String date) throws SQLException {
	log.debug("Fetch vulnerability impact");
	String dateCriteria = getReportDateCriteria(timeInterval, date);
	Statement stmt = connBdsVuln.createStatement();

	String sql = queryBuilder.getQueryVulnerabilityImpact(dateCriteria);
	List<VulnerabilityMapping> mappingList = new ArrayList<VulnerabilityMapping>();
	log.debug("Executing sql: " + sql);
	ResultSet rs = stmt.executeQuery(sql);
	while (rs.next()) {
	    VulnerabilityMapping mapping = new VulnerabilityMappingImpl(
		    rs.getInt(QueryBuilder.NVD_UPDATE_LOG_NVD_CVE_ID),
		    rs.getInt(QueryBuilder.NVD_RELEASE_MAPPINGS_VERSION_ID),
		    rs.getInt(QueryBuilder.NVD_RELEASE_MAPPINGS_RELEASE_ID));
	    mappingList.add(mapping);
	}
	return mappingList;
    }

    /**
     * Retrieve the component information for the provided release id
     *
     * @param the
     *            component release id
     * @return the component information for the provided release id
     * @throws SQLException
     *             if a database access error occurs or this method is called on
     *             a closed result set
     */
    public List<ComponentPojo> retrieveComponentForRelease(int releaseId)
	    throws SQLException {
	log.debug("Fetching component for release");
	List<ComponentPojo> componentList = new ArrayList<ComponentPojo>();

	Statement stmt = connBdsCatalog.createStatement();
	String sql = queryBuilder.getQueryComponentForRelease(releaseId);
	log.debug("Executing sql: " + sql);
	ResultSet rs = stmt.executeQuery(sql);
	while (rs.next()) {
	    ComponentPojo component = new ComponentPojoImpl(
		    rs.getString(QueryBuilder.COMPONENT_ID),
		    rs.getString(QueryBuilder.COMPONENT_NAME),
		    rs.getString(QueryBuilder.COMPONENT_VERSION),
		    rs.getString(QueryBuilder.COMPONENT_KB_COMPONENT_ID));
	    log.info(component.toString());
	    componentList.add(component);
	}

	if (componentList.size() > 0) {
	    log.info("Components identified for the RELEASE_ID : " + releaseId
		    + " - " + componentList.size());
	}
	return componentList;
    }

    /**
     * Retrieve application information for the provided component id
     *
     * @param componentId
     *            the component id
     * @return the list of applications containing the component
     * @throws SQLException
     *             if a database access error occurs or this method is called on
     *             a closed result set
     */
    public List<ApplicationPojo> retrieveApplicationFromComponentUse(
	    String componentId) throws SQLException {
	log.debug("Fetching application from componentUse");
	List<ApplicationPojo> applicationList = new ArrayList<ApplicationPojo>();

	Statement stmtComponentUse = connBdsCatalog.createStatement();
	Statement stmtApplication = connBdsCatalog.createStatement();

	String sqlComponentUse = queryBuilder
		.getQueryComponentFromComponentUse(componentId);
	log.debug("Executing sql: " + sqlComponentUse);
	ResultSet rs = stmtComponentUse.executeQuery(sqlComponentUse);
	while (rs.next()) {
	    String application = rs
		    .getString(QueryBuilder.COMPONENTUSE_APPLICATION);
	    log.debug("Searching for application: ID = " + application);

	    String sqlApplication = queryBuilder
		    .getQueryAppliaction(application);
	    ResultSet rsApplication = stmtApplication
		    .executeQuery(sqlApplication);
	    while (rsApplication.next()) {
		String applicationName = rsApplication
			.getString(QueryBuilder.APPLICATION_NAME);
		ApplicationPojo applicationPojo = new ApplicationPojoImpl(
			rsApplication.getString(QueryBuilder.APPLICATION_ID),
			applicationName,
			rsApplication
				.getString(QueryBuilder.APPLICATION_VARSION),
			rsApplication.getString(QueryBuilder.APPLICATION_DESCN),
			null);
		applicationList.add(applicationPojo);
		log.debug("Identified application : " + application + ":"
			+ applicationName);
	    }
	}
	return applicationList;
    }

    /**
     * Construct the criteria for the report date
     *
     * @param timeInterval
     *            the time interval specified in the configuration file
     * @param date
     *            the date specified in the configuration file
     * @return the criteria for the report date
     */
    private String getReportDateCriteria(int timeInterval, String date) {
	String dateCriteria = (date != null && !date.trim().isEmpty()) ? "to_char(updated, 'YYYY-MM-DD') = "
		+ date
		: "updated >= (now() - '" + timeInterval + " hour'::INTERVAL) ";
	return dateCriteria;
    }

    private class RemediationDates {
	private Date targetRemediationDate;

	private Date actualRemediationDate;

	public RemediationDates(Date targetRemediationDate,
		Date actualRemediationDate) {
	    this.targetRemediationDate = targetRemediationDate;
	    this.actualRemediationDate = actualRemediationDate;
	}

	public Date getTargetRemediationDate() {
	    return targetRemediationDate;
	}

	public Date getActualRemediationDate() {
	    return actualRemediationDate;
	}

    }
}
