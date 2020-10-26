package de.fraunhofer.iese.ids.ucapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.fraunhofer.iese.ids.odrl.mydata.translator.model.MydataPolicy;
import de.fraunhofer.iese.ids.odrl.mydata.translator.util.MydataTranslator;
import de.fraunhofer.iese.ids.odrl.policy.library.model.OdrlPolicy;
import de.fraunhofer.iese.ids.odrl.policy.library.model.tooling.IdsOdrlUtil;

/**
 * @author Robin Brandstaedter <Robin.Brandstaedter@iese.fraunhofer.de>
 */
@Service
public class PolicyTranslationService {
  private MydataTranslator mydatatranslator;

  @Autowired
  PolicyTranslationService(MydataTranslator mydatatranslator) {
    this.mydatatranslator = mydatatranslator;
  }
  
  public OdrlPolicy createOdrlPoliy(String odrlPolicyString) {
	    return IdsOdrlUtil.getOdrlPolicy(odrlPolicyString);
	  }
  
  public MydataPolicy createMydataPolicy(OdrlPolicy odrlPolicy) {
	    return mydatatranslator.createMydataPolicy(odrlPolicy);
	  }

}
