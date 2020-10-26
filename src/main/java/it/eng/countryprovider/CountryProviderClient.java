package it.eng.countryprovider;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.commons.net.whois.WhoisClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@ConditionalOnProperty(name = "countryProvider.version", havingValue = "client")
public class CountryProviderClient implements CountryProvider {

	@Value("${countryProvider.whois.server}")
	private String whoIsServer;
	@Value("${countryProvider.whois.port}")
	private int whoIsPort = 43;
	@Value("${countryProvider.whois.ipAddress:#{null}}")
	private String ipAddress;

	private String getWhois() {
		StringBuilder result = new StringBuilder("");
		WhoisClient whois = new WhoisClient();
		
		try {
			if(ipAddress == null) {
				InetAddress hostIP = InetAddress.getLocalHost();
				log.debug("Inet address {}", hostIP.getHostAddress());
				ipAddress = hostIP.getHostAddress();
			}
			whois.connect(whoIsServer, whoIsPort);
			log.info("Getting whois for ip '{}'", ipAddress);
			String whoisData1 = whois.query(ipAddress);
			result.append(whoisData1);
			whois.disconnect();
		} catch (IOException e) {
			log.error("Error while calling whois service {}", e);
		}
		return result.toString();
	}

	private String getCountryFromDescription(String processResponse) {
		Stream<String> lines = processResponse.lines();
		Optional<String> result = lines.filter(line -> line.startsWith("country:")).findFirst();
		String country = null;
		if (result.isPresent()) {
			String[] ss = result.get().split(":");
			String possibleCountry = ss[1].trim();
			if(possibleCountry.length() == 2 || possibleCountry.length() == 3) {
				log.info("Country from response {}", country);
				country = possibleCountry;
			} else {
				log.info("Country not obtained from response, leaving value 'null'");
				log.debug("Processed response:\n{}", processResponse);
			}
		}
		return country;
	}

	@Override
	public String getCountry() {
		return getCountryFromDescription(getWhois());
	}

}
