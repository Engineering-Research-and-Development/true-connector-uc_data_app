package it.eng.idsa.policy.service;

import java.util.Map;

public interface PolicyManagementService {
	
	void saveToFile(String policyId, String odrlPolicyString);

	Map<String, String> loadPoliciesFromFilesystem();

}
