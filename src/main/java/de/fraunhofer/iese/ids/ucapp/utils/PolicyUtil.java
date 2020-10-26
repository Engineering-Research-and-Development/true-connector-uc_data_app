/**
 * 
 */
package de.fraunhofer.iese.ids.ucapp.utils;

/**
 * @author Robin Brandstaedter <Robin.Brandstaedter@iese.fraunhofer.de>
 *
 */
public class PolicyUtil {
	
	public static String getMydataPolicyId(String odrlPolicyId) {
		String[] splitedID = odrlPolicyId.split("/");
		String policyID = splitedID[splitedID.length - 1];
		return "urn:policy:ids:" + policyID;
	}

}
