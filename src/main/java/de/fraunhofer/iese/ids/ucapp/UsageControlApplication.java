package de.fraunhofer.iese.ids.ucapp;

import de.fraunhofer.iese.ids.odrl.mydata.translator.util.MydataTranslator;
import de.fraunhofer.iese.mydata.eventhistory.EnableEventHistory;
import de.fraunhofer.iese.mydata.pep.EnablePolicyEnforcementPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Robin Brandstädter (Robin.Brandstaedter@iese.fraunhofer.de)
 */
@SpringBootApplication
@EnablePolicyEnforcementPoint(basePackages = "de.fraunhofer.iese.ids.ucapp.mydata")
@EnableEventHistory
@ComponentScan(basePackages = {"it.eng", "de.fraunhofer.iese.ids.ucapp"})
public class UsageControlApplication {
  private static final Logger LOG = LoggerFactory.getLogger(UsageControlApplication.class);

  public static void main(String[] args) {
    LOG.info("starting UsageControlApplication");
    SpringApplication.run(UsageControlApplication.class, args);
  }
  
  @Bean
  public MydataTranslator mydataTranslator() {
	  return new MydataTranslator();
  }
}


