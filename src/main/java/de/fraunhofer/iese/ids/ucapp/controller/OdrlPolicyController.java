package de.fraunhofer.iese.ids.ucapp.controller;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.fraunhofer.iese.ids.ucapp.model.entity.OdrlPolicyPersistence;
import de.fraunhofer.iese.ids.ucapp.service.PolicyService;
import de.fraunhofer.iese.mydata.exception.ConflictingResourceException;
import de.fraunhofer.iese.mydata.exception.InvalidEntityException;
import de.fraunhofer.iese.mydata.exception.NoSuchEntityException;
import de.fraunhofer.iese.mydata.exception.ResourceUpdateException;

/**
 * @author Robin Brandstaedter <Robin.Brandstaedter@iese.fraunhofer.de>
 */
@RestController
@RequestMapping(path = "policy/usage")
public class OdrlPolicyController {
  private final static Logger LOG = LoggerFactory.getLogger(OdrlPolicyController.class);
  private final PolicyService policyService;

  @Autowired
  public OdrlPolicyController(PolicyService policyService) {
    this.policyService = policyService;
  }

  @PostMapping(value = "/odrl", consumes = {"application/ld+json;charset=UTF-8"})
  public ResponseEntity<String> addPolicy(@RequestBody String policy) {
	  String mydataString = policyService.addOdrlPolicy(policy);
    return ResponseEntity.ok(mydataString);
  }
  
  @GetMapping(value = {"/odrl","/odrl/"})
  public ResponseEntity<List<OdrlPolicyPersistence>> getAllPolicies() throws NoSuchEntityException{
	  return ResponseEntity.ok(policyService.getAllOdrlPolicyPersistence());
  }
  
  @DeleteMapping(value = {"/odrl","/odrl/"})
  public ResponseEntity<Void> deletePolicy(@RequestBody String policyId) throws NoSuchElementException, IOException, ResourceUpdateException, InvalidEntityException, NoSuchEntityException, ConflictingResourceException{
	  policyService.deleteOdrlPolicyPersistence(policyId);
	  return ResponseEntity.ok().build();
  }
  
  
}
