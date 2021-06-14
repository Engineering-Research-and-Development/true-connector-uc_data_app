package it.eng.idsa.policy.service;

import java.util.Map;

public interface PolicyManagementService {
	
	public void saveToFile(String policyId, String odrlPolicyString);

	public Map<String, String> loadPoliciesFromFilesystem();

}
