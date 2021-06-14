package de.fraunhofer.iese.ids.ucapp.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import de.fraunhofer.iese.ids.odrl.policy.library.model.OdrlPolicy;
import de.fraunhofer.iese.ids.ucapp.model.entity.OdrlPolicyPersistence;
import de.fraunhofer.iese.ids.ucapp.utils.PolicyUtil;
import de.fraunhofer.iese.mydata.MyDataEnvironment;
import de.fraunhofer.iese.mydata.component.interfaces.IBasicManagementService;
import de.fraunhofer.iese.mydata.exception.ConflictingResourceException;
import de.fraunhofer.iese.mydata.exception.InvalidEntityException;
import de.fraunhofer.iese.mydata.exception.ResourceUpdateException;
import de.fraunhofer.iese.mydata.policy.Policy;
import de.fraunhofer.iese.mydata.policy.PolicyId;
import it.eng.policy.service.PolicyManagementService;

/**
 * @author Robin Brandstaedter <Robin.Brandstaedter@iese.fraunhofer.de>
 */
@Service
public class PolicyService {
  private static final Logger LOG = LoggerFactory.getLogger(PolicyService.class);

  private final MyDataEnvironment myDataEnvironment;
  private final PolicyTranslationService policyTranslationService;
  private final OdrlPolicyPersistenceService odrlPolicyPersistenceService;
  private final PolicyManagementService policyManagementService;
  
  private boolean savePoliciesToFilestorage;

  @Autowired
  public PolicyService(MyDataEnvironment myDataEnvironment, PolicyTranslationService policyTranslationService,
			OdrlPolicyPersistenceService odrlPolicyPersistenceService, @Nullable PolicyManagementService policyManagementService,
			@Value("${application.savePoliciesToFilestorage}") boolean savePoliciesToFilestorage) {
		this.myDataEnvironment = myDataEnvironment;
		this.policyTranslationService = policyTranslationService;
		this.odrlPolicyPersistenceService = odrlPolicyPersistenceService;
		this.policyManagementService = policyManagementService;
		this.savePoliciesToFilestorage = savePoliciesToFilestorage;
  }

  @PostConstruct
  private void loadPoliciesOnStartup() {
	if (savePoliciesToFilestorage) {
		LOG.info("Loading policies on startup...");
		Map<String, String> policies = policyManagementService.loadPoliciesFromFilesystem();
		if (null != policies) {
			for (String policy : policies.values()) {
				addOdrlPolicy(policy, false);
			}
			LOG.info("Added all policies from file system.");
		} else {
			LOG.info("No policies were added.");
		} 
	}
  }

  public String addOdrlPolicy(final String odrlPolicyString, boolean newPolicy) { // TODO what should be returned? maybe also declare a checked exception?
    // TODO Auto-generated method stub
	OdrlPolicy odrlPolicy =  policyTranslationService.createOdrlPoliy(odrlPolicyString); 
	  
    final String myDataPolicyString = this.policyTranslationService.createMydataPolicy(odrlPolicy).toString();// translate
    
    final IBasicManagementService pmp = myDataEnvironment.getPmp();
    boolean deploymentSucceeded = false;
    try {
      final Policy myDataPolicy = new Policy(myDataPolicyString); // create
      final PolicyId myDataPolicyId = pmp.addPolicy(myDataPolicy); // submit to PMP
      pmp.deployPolicy(myDataPolicyId); // deploy policy
      LOG.info("Successfully deployed policy. Odrl was: \n###\n{}\n###\nMyData is:\n###\n{}\n###", odrlPolicyString, myDataPolicyString);
      deploymentSucceeded = true;
    } catch (ConflictingResourceException | InvalidEntityException | ResourceUpdateException | IOException e) {
      LOG.error("Problem while adding the new policy. Odrl was: \n###\n{}\n###\nMyData should be:\n###\n{}\n###", odrlPolicyString, myDataPolicyString, e);
    }
    if (deploymentSucceeded) {
    	this.odrlPolicyPersistenceService.addOrUpdate(odrlPolicy.getPolicyId().toString(), odrlPolicyString);
    	if (newPolicy && savePoliciesToFilestorage) {
			policyManagementService.saveToFile(odrlPolicy.getPolicyId().toString(), odrlPolicyString);
		}
	return myDataPolicyString;
    } else {
      return null;
    }
  }
  
  public List<OdrlPolicyPersistence> getAllOdrlPolicyPersistence(){
	for(OdrlPolicyPersistence odrlPolicyPersistence : odrlPolicyPersistenceService.getAll()) {
		String mydataPolicyIdString = PolicyUtil.getMydataPolicyId(odrlPolicyPersistence.getPolicyId());
		PolicyId mydataPolicyId = new PolicyId(mydataPolicyIdString);
		try {
			if(null == myDataEnvironment.getPmp().getPolicy(mydataPolicyId)) {
				
			}
		} catch (NoSuchElementException e) {
			odrlPolicyPersistenceService.delete(odrlPolicyPersistence.getPolicyId());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	  
  	return odrlPolicyPersistenceService.getAll();
  }
  
  public  void deleteOdrlPolicyPersistence(String id) throws NoSuchElementException, IOException, ResourceUpdateException, InvalidEntityException{
	  	boolean isdeleted = false;
	  	PolicyId mydataPolicyId = new PolicyId(PolicyUtil.getMydataPolicyId(id));
	  	try {
			myDataEnvironment.getPmp().revokePolicy(mydataPolicyId);
		} catch (ResourceUpdateException | InvalidEntityException e) {
			LOG.info("Policy could not be revoked {} \n {}", id, e.getMessage());
		}
	  		if(! myDataEnvironment.getPmp().isPolicyDeployed(mydataPolicyId)) {
	  			myDataEnvironment.getPmp().deletePolicy(mydataPolicyId);
	  			isdeleted = true;
	  		}

	  	if(isdeleted) {
	  	odrlPolicyPersistenceService.delete(id);
	  	}
	  	else {
	  		throw new ResourceUpdateException("Policy could not be deleted: " + id);
	  	}
  }
  
}
