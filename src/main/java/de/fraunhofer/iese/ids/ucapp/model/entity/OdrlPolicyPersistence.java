/**
 * 
 */
package de.fraunhofer.iese.ids.ucapp.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

/**
 * @author Robin Brandstaedter <Robin.Brandstaedter@iese.fraunhofer.de>
 *
 */
@Entity
@Data
public class OdrlPolicyPersistence {

	@Id
	private String policyId;
	
	@Column( length = 50000 )
	private String odrlPolicyAsString;
	
}
