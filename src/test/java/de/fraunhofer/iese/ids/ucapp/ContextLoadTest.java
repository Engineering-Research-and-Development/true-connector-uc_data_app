package de.fraunhofer.iese.ids.ucapp;

import de.fraunhofer.iese.mydata.MyDataEnvironment;
import de.fraunhofer.iese.mydata.pdp.interfaces.AbstractEventRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContextLoadTest {

  @Autowired
  private AbstractEventRepository eventRepository;
  @Autowired
  private MyDataEnvironment myDataEnvironment;

  @Test
  public void contextLoads() {
    Assert.assertNotNull(myDataEnvironment);
    Assert.assertNotNull(eventRepository);
  }

}
