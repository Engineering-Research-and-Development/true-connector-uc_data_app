package eng.it.countryprovider;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import it.eng.idsa.countryprovider.CountryProviderClient;

public class CountryProviderClientTest {
	
	private CountryProviderClient cProvider;
	
	@Before
	public void setup() {
		cProvider = new CountryProviderClient();
		ReflectionTestUtils.setField(cProvider, "whoIsServer", "whois.ripe.net");
		ReflectionTestUtils.setField(cProvider, "whoIsPort", 43);
	}
	
	@Test
	public void getCountryRS() {
		ReflectionTestUtils.setField(cProvider, "ipAddress", "87.116.161.249");
		String country = cProvider.getCountry();
		assertNotNull(country);
		assertEquals("RS", country);
	}
	
	@Test
	public void getCountryIT() {
		ReflectionTestUtils.setField(cProvider, "ipAddress", "87.11.10.20");
		String country = cProvider.getCountry();
		assertNotNull(country);
		assertEquals("IT", country);
	}

}
