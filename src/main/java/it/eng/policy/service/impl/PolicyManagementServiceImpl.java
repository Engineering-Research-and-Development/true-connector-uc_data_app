package it.eng.policy.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import it.eng.policy.service.PolicyManagementService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@ConditionalOnProperty(name = "application.savePoliciesToFilestorage", havingValue = "true")
public class PolicyManagementServiceImpl implements PolicyManagementService {
	
	@Value("${application.odrlPolicyDirectory}")
	private String directory;

	@Override
	public void saveToFile(String policyId, String odrlPolicyString) {
		String policyName = policyId.substring(policyId.lastIndexOf("/")+1);
		File file = new File(directory + FileSystems.getDefault().getSeparator() + policyName);
		try (FileWriter fw = new FileWriter(file);){
			log.info("Saving policy {} to disk...", policyName);
			fw.write(odrlPolicyString);
			log.info("Policy saved.");
		} catch (IOException e) {
			log.error("Policy could not be saved {} ", e);
		} 
	}

	@Override
	public Map<String, String> loadPoliciesFromFilesystem() {
		log.info("Starting to load policies from disk...");
		Map<String, String> policies = new HashMap<>();
		File[] files = new File(directory).listFiles();
		if (ArrayUtils.isNotEmpty(files)) {
			for (File file : files) {
				try {
					policies.put(file.getName(), FileUtils.readFileToString(file, "UTF-8"));
				} catch (IOException e) {
					log.error("Unable to read file {} because of {}", file.getName(), e);
				}
			}
			log.info("All policies loaded.");
			return policies;
		} else {
			log.info("No policies found.");
			return null;
		}
	}
	
}
