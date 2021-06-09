package de.fraunhofer.iese.ids.ucapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import de.fraunhofer.iese.ids.odrl.mydata.translator.util.MydataTranslator;
import de.fraunhofer.iese.mydata.IMyDataEnvironment;
import de.fraunhofer.iese.mydata.MyDataEnvironmentManager;
import de.fraunhofer.iese.mydata.eventhistory.EnableEventHistory;
import de.fraunhofer.iese.mydata.exception.InitializationException;
import de.fraunhofer.iese.mydata.pep.EnablePolicyEnforcementPoint;
import de.fraunhofer.iese.mydata.pip.EnablePolicyInformationPoint;
import de.fraunhofer.iese.mydata.pxp.EnablePolicyExecutionPoint;
import de.fraunhofer.iese.mydata.solution.SolutionId;

/**
 * @author Robin Brandstädter (Robin.Brandstaedter@iese.fraunhofer.de)
 */
@SpringBootApplication
@EnablePolicyEnforcementPoint(basePackages = "de.fraunhofer.iese.ids.ucapp.mydata")
@EnablePolicyInformationPoint
@EnablePolicyExecutionPoint
@EnableEventHistory
@ComponentScan(basePackages = {"it.eng.countryprovider", "de.fraunhofer.iese.ids.ucapp"})
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
  
  @Bean
  IMyDataEnvironment myDataEnvironment() throws InitializationException {
	  return  MyDataEnvironmentManager.constructDefaultEnvironment().initializeLocal(new SolutionId("urn:solution:ids"), "Europe/Berlin", 4, true, null);
  }
}


