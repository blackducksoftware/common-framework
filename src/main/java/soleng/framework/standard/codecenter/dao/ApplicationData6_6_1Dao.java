package soleng.framework.standard.codecenter.dao;

import java.util.List;
import java.util.SortedSet;

import soleng.framework.standard.codecenter.pojo.ApplicationPojo;
import soleng.framework.standard.codecenter.pojo.ComponentPojo;
import soleng.framework.standard.codecenter.pojo.ComponentUsePojo;
import soleng.framework.standard.codecenter.pojo.VulnerabilityPojo;

@Deprecated
public interface ApplicationData6_6_1Dao {
	public List<ApplicationPojo> getApplications() throws Exception;
	public ApplicationPojo getApplication(String appName, String version) throws Exception;
	public List<ComponentUsePojo> getComponentUses(ApplicationPojo application) throws Exception;
	public List<ComponentPojo> getComponents(ApplicationPojo application) throws Exception;
	public SortedSet<ComponentPojo> getComponents(String appId) throws Exception;
	public ComponentPojo getComponent(ComponentUsePojo componentUse) throws Exception;
	public List<VulnerabilityPojo> getVulnerabilities(ComponentPojo component, ComponentUsePojo compUse) throws Exception;
	public void updateCompUseVulnData(ComponentUsePojo compUse, VulnerabilityPojo vuln) throws Exception;
	public void limitNumberOfApplications(int maxNumberApplications);
	public void close() throws Exception;
}
