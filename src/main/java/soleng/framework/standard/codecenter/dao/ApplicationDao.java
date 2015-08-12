package soleng.framework.standard.codecenter.dao;

import java.util.List;
import java.util.SortedSet;

import soleng.framework.standard.codecenter.pojo.ApplicationPojo;
import soleng.framework.standard.codecenter.pojo.ComponentPojo;
import soleng.framework.standard.codecenter.pojo.ComponentUsePojo;
import soleng.framework.standard.codecenter.pojo.VulnerabilityPojo;

/**
 * Manages components/vulnerabilities/metadata for a single application.
 * @author sbillings
 *
 */
public interface ApplicationDao {

	public ApplicationPojo getApplication() throws Exception;
	public List<ComponentUsePojo> getComponentUses() throws Exception;
	public List<ComponentPojo> getComponents() throws Exception;
	public SortedSet<ComponentPojo> getComponentsSorted() throws Exception;
	public ComponentPojo getComponent(ComponentUsePojo componentUse) throws Exception;
	public List<VulnerabilityPojo> getVulnerabilities(ComponentPojo component, ComponentUsePojo compUse) throws Exception;
	public SortedSet<VulnerabilityPojo> getVulnerabilitiesSorted(ComponentPojo compPojo, ComponentUsePojo compUsePojo) throws Exception;
	public void updateCompUseVulnData(ComponentUsePojo compUse, VulnerabilityPojo vuln) throws Exception;
}
