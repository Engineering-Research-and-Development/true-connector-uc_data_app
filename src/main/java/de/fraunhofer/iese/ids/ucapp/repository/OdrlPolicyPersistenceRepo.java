package de.fraunhofer.iese.ids.ucapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import de.fraunhofer.iese.ids.ucapp.model.entity.OdrlPolicyPersistence;
/**
 * 
 * @author Robin Brandstaedter <Robin.Brandstaedter@iese.fraunhofer.de>
 *
 */
public interface OdrlPolicyPersistenceRepo extends JpaRepository<OdrlPolicyPersistence, Long> {
	
	Optional<OdrlPolicyPersistence> findByPolicyId(String policyId);
}
