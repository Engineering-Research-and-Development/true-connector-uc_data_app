package de.fraunhofer.iese.ids.ucapp.service;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.fraunhofer.iese.ids.odrl.policy.library.model.OdrlPolicy;
import de.fraunhofer.iese.ids.ucapp.model.entity.OdrlPolicyPersistence;
import de.fraunhofer.iese.ids.ucapp.utils.PolicyUtil;
import de.fraunhofer.iese.mydata.IMyDataEnvironment;
import de.fraunhofer.iese.mydata.component.interfaces.IBasicManagementService;
import de.fraunhofer.iese.mydata.exception.ConflictingResourceException;
import de.fraunhofer.iese.mydata.exception.InvalidEntityException;
import de.fraunhofer.iese.mydata.exception.NoSuchEntityException;
import de.fraunhofer.iese.mydata.exception.ResourceUpdateException;
import de.fraunhofer.iese.mydata.policy.Policy;
import de.fraunhofer.iese.mydata.policy.PolicyId;

/**
 * @author Robin Brandstaedter <Robin.Brandstaedter@iese.fraunhofer.de>
 */
@Service
public class PolicyService {
  private static final Logger LOG = LoggerFactory.getLogger(PolicyService.class);

  private final IMyDataEnvironment myDataEnvironment;
  private final PolicyTranslationService policyTranslationService;
  private final OdrlPolicyPersistenceService odrlPolicyPersistenceService;

  @Autowired
  public PolicyService(IMyDataEnvironment myDataEnvironment, PolicyTranslationService policyTranslationService, OdrlPolicyPersistenceService odrlPolicyPersistenceService) {
    this.myDataEnvironment = myDataEnvironment;
    this.policyTranslationService = policyTranslationService;
    this.odrlPolicyPersistenceService = odrlPolicyPersistenceService;
  }

  public String addOdrlPolicy(final String odrlPolicyString) { // TODO what should be returned? maybe also declare a checked exception?
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
    } catch (ConflictingResourceException | InvalidEntityException | ResourceUpdateException | IOException | NoSuchEntityException e) {
      LOG.error("Problem while adding the new policy. Odrl was: \n###\n{}\n###\nMyData should be:\n###\n{}\n###", odrlPolicyString, myDataPolicyString, e);
    }
    if (deploymentSucceeded) {
    	this.odrlPolicyPersistenceService.addOrUpdate(odrlPolicy.getPolicyId().toString(), odrlPolicyString);
      return myDataPolicyString;
    } else {
      return null;
    }
  }
  
  public List<OdrlPolicyPersistence> getAllOdrlPolicyPersistence() throws NoSuchEntityException{
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
  
  public  void deleteOdrlPolicyPersistence(String id) throws NoSuchElementException, IOException, ResourceUpdateException, InvalidEntityException, NoSuchEntityException, ConflictingResourceException{
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
