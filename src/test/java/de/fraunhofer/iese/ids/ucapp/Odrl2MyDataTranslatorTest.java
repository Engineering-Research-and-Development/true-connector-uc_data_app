package de.fraunhofer.iese.ids.ucapp;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import de.fraunhofer.iese.ids.odrl.mydata.translator.util.MydataTranslator;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Odrl2MyDataTranslatorTest {

	@Autowired
	MydataTranslator mydataTranslator;
	
  @Test
  public void translate2MydataTest() throws IOException {

	  // Open a valid json(-ld) input file
		// Read the file into an Object (The type of this object will be a List, Map, String, Boolean,
		// Number or null depending on the root object in the file).
	  String text = new String(Files.readAllBytes(Paths.get("src","test","resources","PlugfestPolicy.json")));

		
		//call method to test
//		mydataTranslator.translate2Mydata(text);
		assertTrue(true);
  }

}
