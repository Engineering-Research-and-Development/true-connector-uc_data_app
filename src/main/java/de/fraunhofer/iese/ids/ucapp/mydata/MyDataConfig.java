package de.fraunhofer.iese.ids.ucapp.mydata;

import de.fraunhofer.iese.mydata.MyDataEnvironment;
import de.fraunhofer.iese.mydata.component.ComponentId;
import de.fraunhofer.iese.mydata.exception.ConflictingResourceException;
import de.fraunhofer.iese.mydata.exception.InvalidEntityException;
import de.fraunhofer.iese.mydata.exception.ResourceUpdateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

import javax.annotation.PostConstruct;

@Configuration
public class MyDataConfig {

  private static final Logger LOG = LoggerFactory.getLogger(MyDataConfig.class);
  private final MyDataEnvironment myDataEnvironment;
  private final PurposePip purposePip;
  private final SpatialPip spatialPip;

  @Autowired
  public MyDataConfig(MyDataEnvironment myDataEnvironment, PurposePip purposePip, SpatialPip spatialPip) {
    this.myDataEnvironment = myDataEnvironment;
    this.purposePip = purposePip;
    this.spatialPip = spatialPip;
  }

  @PostConstruct
  public void postConstruct() {
    try {
      myDataEnvironment.registerLocalPip(new ComponentId("urn:component:ids:pip:purpose-pip"), purposePip);
      myDataEnvironment.registerLocalPip(new ComponentId("urn:component:ids:pip:spatial-pip"), spatialPip);
    } catch (InvalidEntityException | ResourceUpdateException | ConflictingResourceException | IOException e) {
      LOG.error(e.getMessage(), e);
    }

//    try {
//      final String myDataPolicyString = "<policy id='urn:policy:ids:usage-data-ABCDEF-123456' description='This is the generated access policy for data ABCDEF-123456. Time of generation: 2019-06-06T12:41:26.107437Z' xmlns='http://www.mydata-control.de/4.0/mydataLanguage' xmlns:tns='http://www.mydata-control.de/4.0/mydataLanguage' xmlns:parameter='http://www.mydata-control.de/4.0/parameter' xmlns:pip='http://www.mydata-control.de/4.0/pip' xmlns:function='http://www.mydata-control.de/4.0/function' xmlns:event='http://www.mydata-control.de/4.0/event' xmlns:constant='http://www.mydata-control.de/4.0/constant' xmlns:variable='http://www.mydata-control.de/4.0/variable' xmlns:variableDeclaration='http://www.mydata-control.de/4.0/variableDeclaration' xmlns:valueChanged='http://www.mydata-control.de/4.0/valueChanged' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xmlns:date='http://www.mydata-control.de/4.0/date' xmlns:time='http://www.mydata-control.de/4.0/time' xmlns:day='http://www.mydata-control.de/4.0/day'>\n" +
//          "  <mechanism event='urn:action:ids:use'>\n" +
//          "    <if>\n" +
//          "      <and>\n" +
//          "        <equals>\n" +
//          "          <constant:string value='http://example/ids-data:ABCDEF-123456'/>\n" +
//          "          <event:string eventParameter='TargetDataUri' default=''/>\n" +
//          "        </equals>\n" +
//          "        <or> \n" +
//          "          <and> \n" +
//          "            <date is='exactly' value='10.06.2019'/> \n" +
//          "            <time is='after' value='08:00'/> \n" +
//          "          </and> \n" +
//          "          <date is='after' value='10.06.2019'/> \n" +
//          "        </or> \n" +
//          "        <or> \n" +
//          "          <date is='before' value='20.06.2019'/> \n" +
//          "          <and> \n" +
//          "            <date is='exactly' value='20.06.2019'/> \n" +
//          "            <time is='before' value='18:00'/> \n" +
//          "          </and> \n" +
//          "        </or>\n" +
//          "        <equals>\n" +
//          "          <constant:string value='DE'/>\n" +
//          "          <pip:string method='urn:info:ids:spatial' default='none'/>\n" +
//          "        </equals>\n" +
//          "        <equals>\n" +
//          "          <constant:string value='http://example.com/ids-purpose:Marketing'/>\n" +
//          "          <pip:string method='urn:info:ids:purpose' default='none'>\n" +
//          "            <parameter:string name='MsgTargetAppUri'>\n" +
//          "              <event:string eventParameter='MsgTarget' default='0' jsonPathQuery='$.AppUri'/>\n" +
//          "            </parameter:string>\n" +
//          "          </pip:string>\n" +
//          "        </equals>\n" +
//          "      </and>\n" +
//          "      <then>\n" +
//          "        <allow/>\n" +
//          "      </then>\n" +
//          "    </if>\n" +
//          "    <elseif>\n" +
//          "      <equals>\n" +
//          "        <constant:string value='http://example/ids-data:ABCDEF-123456'/>\n" +
//          "        <event:string eventParameter='TargetDataUri' default=''/>\n" +
//          "      </equals>\n" +
//          "      <then>\n" +
//          "        <inhibit/>\n" +
//          "      </then>\n" +
//          "    </elseif>\n" +
//          "  </mechanism>\n" +
//          "</policy>";
//      myDataEnvironment.getPmp().deployPolicy(myDataEnvironment.getPmp().addPolicy(new Policy(myDataPolicyString)));
//    } catch (ConflictingResourceException | InvalidEntityException | ResourceUpdateException | IOException e) {
//      LOG.error(e.getMessage(), e);
//    }
  }
}
