package de.fraunhofer.iese.ids.ucapp;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import de.fraunhofer.iese.mydata.IMyDataEnvironment;
import de.fraunhofer.iese.mydata.MyDataEnvironmentManager;
import de.fraunhofer.iese.mydata.pdp.interfaces.AbstractEventRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContextLoadTest {

	@Autowired
	private AbstractEventRepository eventRepository;
	@Autowired
	private IMyDataEnvironment myDataEnvironment;

	@Test
	public void contextLoads() {
		// added following line because of
		// Caused by: de.fraunhofer.iese.mydata.exception.InitializationException:
		// IMyDataEnvironment with id mydata-default-environment already exists. When
		// you are writing tests, you can enable this via
		// MyDataEnvironmentManager::enableOverwritingOfExistingMyDataEnvironments
		MyDataEnvironmentManager.enableOverwritingOfExistingMyDataEnvironments();
		
		Assert.assertNotNull(myDataEnvironment);
		Assert.assertNotNull(eventRepository);
	}

}
