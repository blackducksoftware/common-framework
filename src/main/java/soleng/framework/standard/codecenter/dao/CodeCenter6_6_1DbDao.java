package soleng.framework.standard.codecenter.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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

import soleng.framework.standard.codecenter.pojo.ApplicationPojoImpl;
import soleng.framework.standard.codecenter.pojo.ComponentImpactImpl;
import soleng.framework.standard.codecenter.pojo.ComponentPojoImpl;
import soleng.framework.standard.codecenter.pojo.ApplicationPojo;
import soleng.framework.standard.codecenter.pojo.ComponentImpact;
import soleng.framework.standard.codecenter.pojo.ComponentPojo;
import soleng.framework.standard.codecenter.pojo.ComponentUsePojo;
import soleng.framework.standard.codecenter.pojo.VulnerabilityMapping;
import soleng.framework.standard.codecenter.pojo.VulnerabilityPojo;
import soleng.framework.standard.codecenter.pojo.VulnerabilityMappingImpl;
import soleng.framework.standard.codecenter.pojo.VulnerabilityPojoImpl;

import soleng.framework.standard.codecenter.dao.QueryBuilder;


/**
 * Code Center Data Access Object for fields that can only be accessed by
 * going direct to the database.
 * 
 * @author Steve Billings
 * @date Oct 29, 2014
 *
 */
@Deprecated
public class CodeCenter6_6_1DbDao {
	private Connection connBdsVuln;
	private Connection connBdsCatalog;
	private Map<Long, String> vulnStatusCache;

	private static Logger log = LoggerFactory.getLogger(CodeCenter6_6_1DbDao.class);

	public CodeCenter6_6_1DbDao(CodeCenterDaoConfigManager config) throws SQLException {
		connBdsVuln = getDbConnection(config, "bds_vuln");
		connBdsCatalog = getDbConnection(config, "bds_catalog");
		vulnStatusCache = new HashMap<Long, String>(16);
	}
	
	private Connection getDbConnection(CodeCenterDaoConfigManager config, String databaseName) throws SQLException {
		log.debug("Opening database connection to " + databaseName);
		String url = "jdbc:postgresql://" + config.getCcDbServerName() + ":" + config.getCcDbPort() + "/" + databaseName;
		Properties props = new Properties();
		props.setProperty("user", config.getCcDbUserName());
		props.setProperty("password", config.getCcDbPassword());
		Connection conn = DriverManager.getConnection(url, props);
		return conn;
	}
	
	/**
	 * Use this to set on a new vulnerability all fields fetched FROM DB
	 * @param vuln
	 * @param compUse
	 * @throws SQLException
	 */
	public void setDbFields(VulnerabilityPojo vuln, ComponentUsePojo compUse) throws SQLException {
		setDefaults(vuln);
		addCvssScore(vuln);
		addStatus(vuln, compUse);
		addRemediationDates(compUse, vuln);
	}
	
	/** 
	 * Use this to update on a re-used vulnerability only the fields that are use-specific.
	 * @param vuln
	 * @param compUse
	 * @throws SQLException
	 */
	public void setVulnStatusFields(VulnerabilityPojo vuln, ComponentUsePojo compUse) throws SQLException {
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
	
	private void addCvssScore(VulnerabilityPojo vuln) throws SQLException {
		log.debug("Fetching cvss_score from vulnerabilities table for vuln id: " + vuln.getId());
		Statement stmt = connBdsVuln.createStatement();
		String sql = "SELECT cvss_score FROM vulnerabilities WHERE nvd_cve_id = " + vuln.getId();
		ResultSet rs = stmt.executeQuery(sql);
		String cvssScore="<not set>";
		rs.next();
	    cvssScore  = rs.getString("cvss_score");
		
//		vuln.setCvssScore(cvssScore); // Removing Cvss score from vuln pojo; does not belong there
	}
	
	private void addStatus(VulnerabilityPojo vuln, ComponentUsePojo compUse) throws SQLException {
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
		
		log.debug("Fetching vulnerability status name from vulnerability_status table for vulnStatusId: " + vulnStatusId);
		Statement stmt = connBdsCatalog.createStatement();
		String sql = "SELECT name FROM vulnerability_status WHERE id = " + vulnStatusId;
		ResultSet rs = stmt.executeQuery(sql);
		if (rs.next()) {
			vulnStatusString = rs.getString("name");
		}
		
		vulnStatusCache.put(key, vulnStatusString);
		return vulnStatusString;
	}
	
	private long setStatusComment(VulnerabilityPojo vuln, ComponentUsePojo compUse) throws SQLException {
		log.debug("Fetching vulnerability_status_id, comment from componentuser_vulnerability table for vuln ID: " +
				vuln.getId() + " / compUse ID: " + compUse.getId());
		long vulnStatusId = -1;
		Statement stmt = connBdsCatalog.createStatement();
		String sql = "SELECT vulnerability_status_id,comment FROM componentuse_vulnerability " +
				"WHERE vulnerability_id = " + vuln.getId() + " " +
				"AND componentuse_id = '" + compUse.getId() + "'";
		ResultSet rs = stmt.executeQuery(sql);
		if (rs.next()) {
			vulnStatusId = rs.getLong("vulnerability_status_id");
			String vulnStatusComment = rs.getString("comment");
			vuln.setStatusComment(vulnStatusComment);
		}
		return vulnStatusId;
	}
	

	private void addRemediationDates(ComponentUsePojo compUse, VulnerabilityPojo vuln) throws SQLException {

		vuln.setTargetRemediationDate(null);
		vuln.setActualRemediationDate(null);
		RemediationDates dates = getRemediationDates(vuln.getId(), compUse.getId());

		if (dates != null) {
			vuln.setTargetRemediationDate(dates.getTargetRemediationDate());
			vuln.setActualRemediationDate(dates.getActualRemediationDate());
		}
	}
	
	/**
	 * Get remediation dates.
	 * Can use this to check to see whether the componentuse_vulnerabilty record exists yet.
	 * @param vulnId
	 * @param compUseId
	 * @return
	 * @throws SQLException
	 */
	private RemediationDates getRemediationDates(String vulnId, String compUseId) throws SQLException {
		log.debug("Fetching date_remediation, date_completion from componentuse_vulnerability table for vulnId: " +
				vulnId + " / compUseId: " + compUseId);
		RemediationDates dates = null;

		Statement stmt = connBdsCatalog.createStatement();
		String sql = "SELECT date_remediation,date_completion FROM componentuse_vulnerability " +
				"WHERE vulnerability_id = " + vulnId + " " +
				"AND componentuse_id = '" + compUseId + "'";

		try {
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				Date targetRemediationDate = rs.getDate("date_remediation");
				Date actualRemediationDate = rs.getDate("date_completion");
				dates = new RemediationDates(targetRemediationDate, actualRemediationDate);
				log.debug("From DB: target remediation date: " + targetRemediationDate + " (" + getTimeMillis(targetRemediationDate) + ")" +
						"; actual remediation date: " + actualRemediationDate + " (" + getTimeMillis(actualRemediationDate) + ")");
			}
		} catch (SQLException e) {
			log.debug("Error executing SQL: " + sql);
			log.warn("Unable to read componentuse_vulnerability remediation dates, which were added in Code Center 6.7.1p2; " +
					" This is normal when running against older Code Center servers.");
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
	 * Update the componentuse_vulnerability data: vulnerability status, staus comment, and remediation dates.
	 * Update target remediation date on compuse_vulnerability.
	 * @param compUse
	 * @param vuln
	 * @throws Exception
	 */
	public void updateCompUseVulnData(ComponentUsePojo compUse, VulnerabilityPojo vuln)
			throws Exception {

		if (this.getRemediationDates(vuln.getId(), compUse.getId()) != null) {
			updateCompUseVulnDataViaUpdate(compUse, vuln);
		} else {
			updateCompUseVulnDataViaInsert(compUse, vuln);
		}
	}
	
	// Tested, works 10/30 10:21 pm
	private void updateCompUseVulnDataViaUpdate(ComponentUsePojo compUse, VulnerabilityPojo vuln) throws Exception {
		log.debug("Inserting component use vulnerability data");
		StringBuilder assignments = new StringBuilder();
		
		int assignmentCount = 0;
		if (vuln.getStatusId() > 0L) {
			assignments.append("vulnerability_status_id=");
			assignments.append(vuln.getStatusId());
			assignmentCount++;
		}
		
		if ((vuln.getStatusComment() != null) && (vuln.getStatusComment().length() > 0)) {
			if (assignments.length() > 0) {
				assignments.append(",");
			}
			assignments.append("comment='");
			assignments.append(StringEscapeUtils.escapeSql(vuln.getStatusComment()));
			assignments.append("'");
			assignmentCount++;
		}
		if (vuln.getTargetRemediationDate() != null) {
			java.sql.Date targetRemediationDateSql = new java.sql.Date(vuln.getTargetRemediationDate().getTime());
			if (assignments.length() > 0) {
				assignments.append(",");
			}
			assignments.append("date_remediation='");
			assignments.append(targetRemediationDateSql);
			assignments.append("'");
			assignmentCount++;
			log.debug("Setting target remediation date to: " + targetRemediationDateSql + " (" + targetRemediationDateSql.getTime() + ")");
		}
		if (vuln.getActualRemediationDate() != null) {
			java.sql.Date actualRemediationDateSql = new java.sql.Date(vuln.getActualRemediationDate().getTime());
			if (assignments.length() > 0) {
				assignments.append(",");
			}
			assignments.append("date_completion='");
			assignments.append(actualRemediationDateSql);	
			assignments.append("'");
			assignmentCount++;
			log.debug("Setting actual remediation date to: " + actualRemediationDateSql + " (" + actualRemediationDateSql.getTime() + ")");
		}
		if (assignmentCount == 0) {
			return;
		}
		String sql = "UPDATE componentuse_vulnerability SET " + assignments + " WHERE vulnerability_id=" + vuln.getId() + " " +
				"AND componentuse_id='" + compUse.getId() + "'";
		
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

		String exceptionMsg="Empty result set";
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
		throw new Exception("Error deriving next datanase record id by reading hibernate_sequence: " + exceptionMsg);
	}
	
	// Tested, works 10/30 10:21 pm
	private void updateCompUseVulnDataViaInsert(ComponentUsePojo compUse, VulnerabilityPojo vuln) throws Exception {
		log.debug("Updating component use vulnerability data");
		
		Statement stmt = connBdsCatalog.createStatement();
		long databaseRowId = getNextDatabaseRowId();
		
		boolean valueToInsert=false;
		
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

		if ((vuln.getStatusComment() != null) && (vuln.getStatusComment().length() > 0)) {
			columnList.append(",comment");
			valuesList.append(",'");
			valuesList.append(StringEscapeUtils.escapeSql(vuln.getStatusComment()));
			valuesList.append("'");
			valueToInsert=true;
		}
		if (vuln.getTargetRemediationDate() != null) {
			java.sql.Date targetRemediationDateSql = new java.sql.Date(vuln.getTargetRemediationDate().getTime());
			columnList.append(",date_remediation");
			valuesList.append(",'");
			valuesList.append(targetRemediationDateSql);
			valuesList.append("'");
			valueToInsert=true;
			log.debug("Setting target remediation date to: " + targetRemediationDateSql + " (" + targetRemediationDateSql.getTime() + ")");
		}
		if (vuln.getActualRemediationDate() != null) {
			java.sql.Date actualRemediationDateSql = new java.sql.Date(vuln.getActualRemediationDate().getTime());
			columnList.append(",date_completion");
			valuesList.append(",'");
			valuesList.append(actualRemediationDateSql);
			valuesList.append("'");
			valueToInsert=true;
			log.debug("Setting actual remediation date to: " + actualRemediationDateSql + " (" + actualRemediationDateSql.getTime() + ")");
		}
		
		if (!valueToInsert) {
			return;
		}
		
		String sql = "INSERT INTO componentuse_vulnerability (" + columnList + ") VALUES (" + valuesList + ")";
		
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
	 * Handles retrieval of all new vulnerabilities 
	 * The Vulnerability report will be generated for provided date or time interval.
	 * If the date (cc.report.oss.date) is defined in the configuration the report will be generated 
	 * for that date, otherwise for the defined time interval (cc.oss.report.time.interval)
	 * @param timeInterval The time period by hours (e.g.: 24 hours)
	 * @param date The date in 'YYYY-MM-DD' format (e.g.: '2014-09-25');
	 * @return the list of new vulnerabilities for provided date or time interval
	 * @throws SQLException if a database access error occurs or this method is called on a closed result set
	 */
//	public Map<VulnerabilityPojo, List<ComponentImpact>> retrieveVulnerabilitiesNew(int timeInterval, String date) throws SQLException {
//		log.debug("Fetching new vulnerabilities");
//		Map<VulnerabilityPojo, List<ComponentImpact>> vulnerabilityData = new HashMap<VulnerabilityPojo, List<ComponentImpact>>();
//		String dateCriteria = getReportDateCriteria(timeInterval, date);
//
//		Statement stmt = connBdsVuln.createStatement();
//		// Builds the query to retrieve the new vulnerabilities for the provided date criteria
//		String sql = QueryBuilder.getQueryVulnerabilityNew(dateCriteria);
//		log.debug("Executing sql: " + sql);
//		ResultSet rs = stmt.executeQuery(sql);
//		while (rs.next()){
//			// Construct the vulnerabilities object
//			VulnerabilityPojo vuln = buildVulnerabilityPojo(rs, 0L, QueryBuilder.VULNERABILITY_STATUS_NEW);			
//			vulnerabilityData.put(vuln, null);
//		}
//		return vulnerabilityData;
//	}
	
	/**
	 * Handles retrieval of all the vulnerabilities that received updates during the specified time interval
	 * The Vulnerability report will be generated for provided date or time interval.
	 * If the date (cc.report.oss.date) is defined in the configuration the report will be generated 
	 * for that date, otherwise for the defined time interval (cc.oss.report.time.interval)
	 * @param timeInterval The time period by hours (e.g.: 24 hours)
	 * @param date The date in 'YYYY-MM-DD' format (e.g.: '2014-09-25');
	 * @return the list of updated vulnerabilities for provided date or time interval
	 * @throws SQLException if a database access error occurs or this method is called on a closed result set
	 */
//	public Map<VulnerabilityPojo, List<ComponentImpact>> retrieveVulnerabilitiesUpdated(int timeInterval, String date) throws SQLException {
//		log.debug("Fetching updated vulnerabilities");
//		Map<VulnerabilityPojo, List<ComponentImpact>> vulnerabilityData = new HashMap<VulnerabilityPojo, List<ComponentImpact>>();
//		String dateCriteria = getReportDateCriteria(timeInterval, date);
//
//		// Builds the query to retrieve the new vulnerabilities for the provided date criteria
//		String sql = QueryBuilder.getQueryVulnerabilityUpdated(dateCriteria);
//		
//		Statement stmt = connBdsVuln.createStatement();
//		log.debug("Executing sql: " + sql);
//		ResultSet rs = stmt.executeQuery(sql);
//		while (rs.next()){
//			// Construct the vulnerabilities object
//			VulnerabilityPojo vuln = buildVulnerabilityPojo(rs, 0L, QueryBuilder.VULNERABILITY_STATUS_UPDATED);
//			vulnerabilityData.put(vuln, null);
//		}
//		return vulnerabilityData;
//	}

	
	/**
	 * 
	 * @param timeInterval
	 * @param date
	 * @return
	 * @throws SQLException if a database access error occurs or this method is called on a closed result set
	 */
	public List<VulnerabilityMapping> retrieveVulnerabilityImpact(int timeInterval, String date) throws SQLException {
		log.debug("Fetch vulnerability impact");
		String dateCriteria = getReportDateCriteria(timeInterval, date);
		Statement stmt = connBdsVuln.createStatement();

		String sql = QueryBuilder.getQueryVulnerabilityImpact(dateCriteria); 
		List<VulnerabilityMapping> mappingList = new ArrayList<VulnerabilityMapping>();
		log.debug("Executing sql: " + sql);
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()){
			VulnerabilityMapping mapping = new VulnerabilityMappingImpl(rs.getInt(QueryBuilder.NVD_UPDATE_LOG_NVD_CVE_ID),
																	rs.getInt(QueryBuilder.NVD_RELEASE_MAPPINGS_VERSION_ID),
																	rs.getInt(QueryBuilder.NVD_RELEASE_MAPPINGS_RELEASE_ID));
			mappingList.add(mapping);
		}
		return mappingList;
	}

	
	/**
	 * Handles retrieval of all the vulnerability by the id
	 * 
	 * @param nvdCveId the vulnerability id
	 * @return the vulnerability for provided id
	 * @throws SQLException if a database access error occurs or this method is called on a closed result set
	 */
//	public VulnerabilityPojo retrieveVulnerability(int nvdCveId) throws SQLException {
//		log.debug("Fetching vulnerability by ID");
//		VulnerabilityPojo vulnerability = null;
//		Statement stmt = connBdsVuln.createStatement();
//		 
//		String sql = QueryBuilder.getQueryVulnerabilityById(nvdCveId);
//		log.debug("Executing sql: " + sql);
//		ResultSet rs = stmt.executeQuery(sql);
//		if (rs.next()) {
//			// Construct the vulnerabilities object
//			vulnerability = new VulnerabilityPojoImpl(
//					Integer.toString(rs.getInt(QueryBuilder.VULNERABILITIES_NVD_CVE_ID)),
//												  rs.getString(QueryBuilder.VULNERABILITIES_NAME),
//												  rs.getString(QueryBuilder.VULNERABILITIES_DSC),
//												  rs.getString(QueryBuilder.VULNERABILITIES_SEVERITY),
//												  rs.getString(QueryBuilder.VULNERABILITIES_CVSS_SCORE),
//												  rs.getDate(QueryBuilder.VULNERABILITIES_PUBLISHED),
//												  rs.getDate(QueryBuilder.VULNERABILITIES_UPDATED),
//												  0L,
//												  "");
//		}
//		return vulnerability;
//	}
	
	
	/**
	 * Retrieve the component information for the provided release id
	 * @param the component release id
	 * @return the component information for the provided release id
	 * @throws SQLException if a database access error occurs or this method is called on a closed result set
	 */
	public List<ComponentPojo> retrieveComponentForRelease(int releaseId) throws SQLException {
		log.debug("Fetching component for release");
		List<ComponentPojo> componentList = new ArrayList<ComponentPojo>();
		
		Statement stmt = connBdsCatalog.createStatement();
		String sql = QueryBuilder.getQueryComponentForRelease(releaseId);
		log.debug("Executing sql: " + sql);
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()){
			ComponentPojo component = new ComponentPojoImpl(rs.getString(QueryBuilder.COMPONENT_ID), 
													    rs.getString(QueryBuilder.COMPONENT_NAME), 
													    rs.getString(QueryBuilder.COMPONENT_VERSION), 
													    rs.getString(QueryBuilder.COMPONENT_KB_COMPONENT_ID));
			log.info(component.toString());
			componentList.add(component) ;
		}
		
		if (componentList.size() > 0){			
			log.info("Components identified for the RELEASE_ID : " + releaseId + " - " + componentList.size());
		}
		return componentList;
	}
	

	/**
	 * Retrieve application information for the provided component id
	 * @param componentId the component id
	 * @return the list of applications containing the component
	 * @throws SQLException if a database access error occurs or this method is called on a closed result set
	 */
	public List<ApplicationPojo> retrieveApplicationFromComponentUse(String componentId) throws SQLException {
		log.debug("Fetching application from componentUse");
		List<ApplicationPojo> applicationList = new ArrayList<ApplicationPojo>();
		
		Statement stmtComponentUse = connBdsCatalog.createStatement();
		Statement stmtApplication = connBdsCatalog.createStatement();
		
		String sqlComponentUse = QueryBuilder.getQueryComponentFromComponentUse(componentId);
		log.debug("Executing sql: " + sqlComponentUse);
		ResultSet rs = stmtComponentUse.executeQuery(sqlComponentUse);
		while (rs.next()){
			String application = rs.getString(QueryBuilder.COMPONENTUSE_APPLICATION);
			log.debug("Searching for application: ID = " + application);

			String sqlApplication = QueryBuilder.getQueryAppliaction(application);
			ResultSet rsApplication = stmtApplication.executeQuery(sqlApplication);
			while (rsApplication.next()){
				String applicationName = rsApplication.getString(QueryBuilder.APPLICATION_NAME);
				ApplicationPojo applicationPojo = new ApplicationPojoImpl(rsApplication.getString(QueryBuilder.APPLICATION_ID), 
																      applicationName, 
																      rsApplication.getString(QueryBuilder.APPLICATION_VARSION), 
																      rsApplication.getString(QueryBuilder.APPLICATION_DESCN), 
																      null);
				applicationList.add(applicationPojo) ;				
				log.debug("Identified application : " + application + ":" + applicationName);
			}
		}
		return applicationList;
	}
	
	
	/**
	 * Handles building the criteria for interval calculation
	 * @return the calculated interval
	 * @throws SQLException the SQL exception
	 */
//	public String retrieveReportInterval(int interval) throws SQLException {
//		log.debug("Fetching report interval");
//		String sql = QueryBuilder.getQueryIntervalWithTimeZone(interval, QueryBuilder.COLUMN_INTERVAL);
//		
//		String reportInterval = null;
//		Statement stmt = connBdsVuln.createStatement();
//		log.debug("Executing sql: " + sql);
//		ResultSet rs = stmt.executeQuery(sql);
//		if (rs.next()){
//			reportInterval = rs.getString(QueryBuilder.COLUMN_INTERVAL);
//		}
//		return reportInterval;
//	}
		
	/**
	 * Return the current time in the database
	 * @return database current time
	 * @throws SQLException SQLException if a database access error occurs
	 */
//	public String retrieveDatabaseCurrentTime() throws SQLException {
//		log.debug("Fetching DB current time");
//		String currentTime = null;
//		String sql =  QueryBuilder.getQueryDatabaseCurrentTime();		
//		Statement stmt = connBdsVuln.createStatement();
//		log.debug("Executing sql: " + sql);
//		ResultSet rs = stmt.executeQuery(sql);
//		if (rs.next()){
//			currentTime = rs.getString(QueryBuilder.COLUMN_CURRENT_TIME);
//		}
//		return currentTime;
//	}
	/**
	 * Construct the criteria for the report date
	 * @param timeInterval the time interval specified in the configuration file
	 * @param date the date specified in the configuration file
	 * @return the criteria for the report date
	 */
	private String getReportDateCriteria(int timeInterval, String date){
		String dateCriteria = (date != null && !date.trim().isEmpty()) ? 
				"to_char(updated, 'YYYY-MM-DD') = " + date :
					"updated >= (now() - '" + timeInterval + " hour'::INTERVAL) ";
		return dateCriteria;
	}
	
	/**
	 * Construct the vulnerability object from the result set
	 * @param resultSet the table of data representing a database result set, which is usually generated by executing a statement that queries the database.
	 * @param status the status of the vulnerability (NEW or UPDATED)
	 * @return the pojo object built from the column values retrieved from the current row
	 * @throws SQLException if the columnIndex is not valid; if a database access error occurs or this method is called on a closed result set
	 */
//	private VulnerabilityPojo buildVulnerabilityPojo(ResultSet resultSet, long statusId, String status) throws SQLException{
//
//		// Construct the vulnerabilities object
//		VulnerabilityPojo vulnerabilityPojo = new VulnerabilityPojoImpl(
//				Integer.toString(resultSet.getInt(QueryBuilder.VULNERABILITIES_NVD_CVE_ID)),
//																    resultSet.getString(QueryBuilder.VULNERABILITIES_NAME),
//																    resultSet.getString(QueryBuilder.VULNERABILITIES_DSC),
//																    resultSet.getString(QueryBuilder.VULNERABILITIES_SEVERITY),
//																    resultSet.getString(QueryBuilder.VULNERABILITIES_CVSS_SCORE),
//																    resultSet.getDate(QueryBuilder.VULNERABILITIES_PUBLISHED),
//																    resultSet.getDate(QueryBuilder.VULNERABILITIES_UPDATED),
//																    statusId, status);
//		return vulnerabilityPojo;
//	}
	
	private class RemediationDates {
		private Date targetRemediationDate=null;
		private Date actualRemediationDate=null;

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
