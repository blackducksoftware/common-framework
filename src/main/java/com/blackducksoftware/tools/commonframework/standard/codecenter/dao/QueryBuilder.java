/**
 * CommonFramework
 *
 * Copyright (C) 2017 Black Duck Software, Inc.
 * http://www.blackducksoftware.com/
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.blackducksoftware.tools.commonframework.standard.codecenter.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Deprecated
public class QueryBuilder {

    private final Logger log = LoggerFactory.getLogger(this.getClass()
	    .getName());

    public static final String VULNERABILITIES = "vulnerabilities";

    public static final String NVD_CVE_RELEASE_DATA = "nvd_cve_release_data";

    public static final String NVD_RELEASE_MAPPINGS = "nvd_release_mappings";

    public static final String NVD_UPDATE = "nvd_update";

    public static final String NVD_UPDATE_LOG = "nvd_update_log";

    public static final String COMPONENT = "component";

    public static final String COMPONENTUSE = "componentuse";

    public static final String APPLICATION = "application";

    // Column names in the vulnerabilities table
    public static final String VULNERABILITIES_PUBLISHED = "published";

    public static final String VULNERABILITIES_NVD_CVE_ID = "nvd_cve_id";

    public static final String VULNERABILITIES_NAME = "name";

    public static final String VULNERABILITIES_DSC = "dsc";

    public static final String VULNERABILITIES_SEVERITY = "severity";

    public static final String VULNERABILITIES_CVSS_SCORE = "cvss_score";

    public static final String VULNERABILITIES_UPDATED = "updated";

    // Column names
    public static final String NVD_UPDATE_LOG_NVD_CVE_ID = "nvd_cve_id";

    public static final String NVD_RELEASE_MAPPINGS_VERSION_ID = "nvd_cve_version_id";

    public static final String NVD_RELEASE_MAPPINGS_RELEASE_ID = "release_id";

    public static final String COMPONENT_ID = "id";

    public static final String COMPONENT_NAME = "name";

    public static final String COMPONENT_VERSION = "version";

    public static final String COMPONENT_KB_COMPONENT_ID = "kb_component_id";

    public static final String COMPONENTUSE_APPLICATION = "application";

    public static final String COMPONENTUSE_COMPONENT = "component";

    public static final String APPLICATION_NAME = "name";

    public static final String APPLICATION_ID = "id";

    public static final String APPLICATION_VARSION = "version";

    public static final String APPLICATION_DESCN = "descn";

    public static final String QUERY_TAG = "*QUERY*: ";

    public static final String COLUMN_INTERVAL = "interval";

    public static final String COLUMN_CURRENT_TIME = "currentTime";

    public static final String TIMEZONE_CONSTRUCT = "'YYYY/MM/DD HH24:MI:SS (TZ)'";

    // New CVE entry type code
    public static final int NVD_ENTRY_TYPE_CODE_NEW = 1;

    // Updated CVE entry type code
    public static final int NVD_ENTRY_TYPE_CODE_UPDATED_CVE = 2;

    // Updated NVD Release Data type code
    public static final int NVD_ENTRY_TYPE_CODE_UPDATED_NVD = 3;

    // New NVD Release type code
    public static final int NVD_ENTRY_TYPE_CODE_NEW_NVD_RELEASE = 4;

    // New CVE mapping type code
    public static final int NVD_ENTRY_TYPE_CODE_NEW_CVE_MAPPING = 5;

    public static final String OPERATOR_EQAULS = "=";

    public static final String OPERATOR_EQAULS_NOT = "<>";

    // The vulnerability status on the report
    public static final String VULNERABILITY_STATUS_NEW = "NEW";

    public static final String VULNERABILITY_STATUS_UPDATED = "UPDATE";

    public static final Map<String, String> QUERY_LIST = new HashMap<String, String>();

    public static final String NEW_VULNERABILITY_QUERY = "";
    static {
	QUERY_LIST.put(NEW_VULNERABILITY_QUERY, APPLICATION);
    }

    /**
     * Handles building the criteria for interval calculation
     *
     * @param interval
     *            the interval to check for records
     * @return the criteria for interval calculation
     */
    public String getQueryIntervalWithTimeZone(int interval, String name) {
	StringBuilder stringBuilder = new StringBuilder();
	stringBuilder.append("SELECT ");
	stringBuilder.append(" to_char((now() - '");
	stringBuilder.append(interval);
	stringBuilder.append(" hour'::INTERVAL),");
	stringBuilder.append(TIMEZONE_CONSTRUCT);
	stringBuilder.append(") ");
	if (name != null && !name.trim().isEmpty()) {
	    stringBuilder.append(" as ");
	    stringBuilder.append(name);
	}
	return logQuery(stringBuilder);
    }

    /**
     * Return the current time in the database
     *
     * @return database current time
     * @throws SQLException
     *             SQLException if a database access error occurs
     */
    public String getQueryDatabaseCurrentTime() throws SQLException {

	StringBuilder stringBuilder = new StringBuilder();
	stringBuilder.append("SELECT ");
	stringBuilder.append("to_char(now(),");
	stringBuilder.append(TIMEZONE_CONSTRUCT);
	stringBuilder.append(") as currentTime");
	return logQuery(stringBuilder);
    }

    /**
     * Build the query to retrieve all new vulnerabilities for the provided date
     *
     * @param dateCriteria
     *            the criteria for the date
     * @return the query to retrieve all new vulnerabilities for the provided
     *         date
     */
    public String getQueryVulnerabilityNew(String dateCriteria) {
	StringBuilder stringBuilder = new StringBuilder();
	stringBuilder.append(getQueryString(QueryBuilder.VULNERABILITIES));
	stringBuilder.append(" WHERE nvd_cve_id IN ");
	stringBuilder.append(" (SELECT nvd_cve_id FROM nvd_update_log WHERE ");
	stringBuilder.append(dateCriteria);
	stringBuilder
		.append(" AND nvd_cve_id IS NOT NULL AND nvd_entry_type_code = ");
	stringBuilder.append(QueryBuilder.NVD_ENTRY_TYPE_CODE_NEW);
	stringBuilder.append(")");
	return logQuery(stringBuilder);
    }

    /**
     * Build the query to retrieve all updated vulnerabilities for the provided
     * date
     *
     * @param dateCriteria
     *            the criteria for the date
     * @return the query to retrieve all updated vulnerabilities for the
     *         provided date
     */
    public String getQueryVulnerabilityUpdated(String dateCriteria) {
	StringBuilder stringBuilder = new StringBuilder();
	stringBuilder.append(QueryBuilder
		.getQueryString(QueryBuilder.VULNERABILITIES));
	stringBuilder.append(" WHERE nvd_cve_id IN ");
	stringBuilder.append(" (SELECT nvd_cve_id FROM nvd_update_log WHERE ");
	stringBuilder.append(dateCriteria);
	stringBuilder.append(" AND nvd_cve_id IS NOT NULL ");
	stringBuilder.append(" AND nvd_cve_version_id IS NULL ");
	stringBuilder.append(" AND nvd_entry_type_code <> ");
	stringBuilder.append(QueryBuilder.NVD_ENTRY_TYPE_CODE_NEW);
	stringBuilder.append(")");
	return logQuery(stringBuilder);
    }

    /**
     * Build the query to retrieve the vulnerability impact information for the
     * provided date
     *
     * @param dateCriteria
     *            the criteria for the date
     * @return the query to retrieve the vulnerability impact information for
     *         the provided date
     */
    public String getQueryVulnerabilityImpact(String dateCriteria) {
	StringBuilder stringBuilder = new StringBuilder();
	stringBuilder
		.append("SELECT log.nvd_cve_id, mappings.nvd_cve_version_id, mappings.release_id ");
	stringBuilder
		.append(" FROM nvd_release_mappings mappings, nvd_update_log log ");
	stringBuilder
		.append(" WHERE mappings.nvd_cve_version_id = log.nvd_cve_version_id ");
	stringBuilder.append(" AND (log.nvd_entry_type_code <> ");
	stringBuilder.append(QueryBuilder.NVD_ENTRY_TYPE_CODE_NEW);
	stringBuilder.append(" AND log.nvd_cve_id is not null ");
	stringBuilder.append(" AND ");
	stringBuilder.append(dateCriteria);
	stringBuilder.append(")");
	return logQuery(stringBuilder);
    }

    /**
     * Build the query to retrieve the component list for the provided release
     * id
     *
     * @param releaseId
     *            the component release id
     * @return the query to retrieve the component list for the provided release
     *         id
     */
    public String getQueryComponentForRelease(int releaseId) {
	StringBuilder stringBuilder = new StringBuilder();
	stringBuilder.append(getQueryString(COMPONENT));
	stringBuilder.append(" WHERE kb_release_id = '");
	stringBuilder.append(releaseId);
	stringBuilder.append("'");
	return logQuery(stringBuilder);
    }

    /**
     * Build the query to retrieve the component information from the
     * COMPONENTUSE table
     *
     * @param componentId
     *            the component id
     * @return the query to retrieve the component information for the component
     *         id
     */
    public String getQueryComponentFromComponentUse(String componentId) {
	StringBuilder stringBuilder = new StringBuilder();
	stringBuilder.append(getQueryString(COMPONENTUSE));
	stringBuilder.append(" WHERE ");
	stringBuilder.append(COMPONENTUSE_COMPONENT);
	stringBuilder.append(" = '");
	stringBuilder.append(componentId);
	stringBuilder.append("'");
	return logQuery(stringBuilder);
    }

    /**
     * Build the query to retrieve the application information for the provided
     * application id
     *
     * @param componentId
     *            the application id
     * @return the query to retrieve the application information for the
     *         application id
     */
    public String getQueryAppliaction(String applicationId) {
	StringBuilder stringBuilder = new StringBuilder();
	stringBuilder.append(getQueryString(QueryBuilder.APPLICATION));
	stringBuilder.append(" WHERE id = '");
	stringBuilder.append(applicationId);
	stringBuilder.append("'");
	return logQuery(stringBuilder);
    }

    /**
     * Build the query to retrieve the vulnerability information for the
     * provided id
     *
     * @param nvdCveId
     *            the vulnerability id
     * @return the query to retrieve the vulnerability information for the id
     */
    public String getQueryVulnerabilityById(int nvdCveId) {
	StringBuilder stringBuilder = new StringBuilder();
	stringBuilder.append(getQueryString(VULNERABILITIES));
	stringBuilder.append(" WHERE nvd_cve_id = ");
	stringBuilder.append(nvdCveId);
	return logQuery(stringBuilder);
    }

    /**
     * Generate query string with the provided table name
     *
     * @param tableName
     *            the table name to be used in the query
     * @return the generated query string
     */
    public static String getQueryString(String tableName) {
	StringBuilder stringBuilder = new StringBuilder();
	stringBuilder.append("SELECT * FROM ");
	stringBuilder.append(tableName);
	return stringBuilder.toString();
    }

    /**
     * Logs the query and returns it as a string representing the data in the
     * built sequence
     *
     * @param stringBuilder
     *            a string builder initialized to the contents of the built
     *            query string.
     * @return the query as a string representing the data in the built sequence
     */
    private String logQuery(StringBuilder stringBuilder) {
	String sql = stringBuilder.toString();
	log.debug(QUERY_TAG + sql);
	return sql;
    }
}
