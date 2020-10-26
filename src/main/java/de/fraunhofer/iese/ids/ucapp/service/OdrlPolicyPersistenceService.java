/**
 * 
 */
package de.fraunhofer.iese.ids.ucapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.fraunhofer.iese.ids.ucapp.model.entity.OdrlPolicyPersistence;
import de.fraunhofer.iese.ids.ucapp.repository.OdrlPolicyPersistenceRepo;

/**
 * @author Robin Brandstaedter <Robin.Brandstaedter@iese.fraunhofer.de>
 *
 */
@Service
public class OdrlPolicyPersistenceService {
	private OdrlPolicyPersistenceRepo odrlPolicyPersistenceRepo;
	
	@Autowired
	public OdrlPolicyPersistenceService(OdrlPolicyPersistenceRepo odrlPolicyPersistenceService) {
		this.odrlPolicyPersistenceRepo = odrlPolicyPersistenceService;
	}

	public void addOrUpdate(String policyId, String jsonLdString) {
		Optional<OdrlPolicyPersistence> optionalOfOpp = odrlPolicyPersistenceRepo.findByPolicyId(policyId);
		if(optionalOfOpp.isPresent()) {
			OdrlPolicyPersistence odrlPolicyPersistence = optionalOfOpp.get();
			odrlPolicyPersistence.setOdrlPolicyAsString(jsonLdString);
			this.odrlPolicyPersistenceRepo.saveAndFlush(odrlPolicyPersistence);
		}else {
			OdrlPolicyPersistence odrlPolicyPersistence = new OdrlPolicyPersistence();
			odrlPolicyPersistence.setPolicyId(policyId);
			odrlPolicyPersistence.setOdrlPolicyAsString(jsonLdString);
			odrlPolicyPersistenceRepo.saveAndFlush(odrlPolicyPersistence);
		}
	}
	
	public void delete(String policyId) {
		Optional<OdrlPolicyPersistence> optionalOfOpp = odrlPolicyPersistenceRepo.findByPolicyId(policyId);
		optionalOfOpp.ifPresent(opp -> this.odrlPolicyPersistenceRepo.delete(opp));
	}
	
	public List<OdrlPolicyPersistence> getAll(){
		return odrlPolicyPersistenceRepo.findAll();
	}
}
