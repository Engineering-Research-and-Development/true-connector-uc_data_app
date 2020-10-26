package it.eng.countryprovider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@ConditionalOnProperty(name = "countryProvider.version", havingValue = "system")
public class CountryProviderSystemProcess implements CountryProvider {

	@Override
	public String getCountry() {
		try {
			return run();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private String run() throws IOException {
		List<String> cmdList = new ArrayList<>();
		// whois "$(curl -s ipinfo.io/ip)" | grep country | sed -E 's/(.*):{1}(.*)/\2/' | sed "s/ //g"
		cmdList.add("/bin/sh");
		cmdList.add("-c");
		cmdList.add(" whois \"$(curl -s ipinfo.io/ip)\" | grep country | sed -E 's/(.*):{1}(.*)/\\2/' | sed \"s/ //g\"");
//		cmdList.add("\"$(curl -s ipinfo.io/ip)\"");
//		cmdList.add("| grep country");
//		cmdList.add("| sed -E 's/(.*):{1}(.*)/\\2/'");
//		cmdList.add("| sed \"s/ //g\"");

		ProcessBuilder pb = new ProcessBuilder().redirectErrorStream(true);
		pb.command(cmdList);
		log.info("Executing process...{}", String.join(" ", cmdList));
		Process process = pb.start();
		StringBuilder result = new StringBuilder(80);
		try (BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
			while (true) {
				String line = in.readLine();
				if (line == null)
					break;
				result.append(line).append(System.lineSeparator());
			}
		}
		log.info("Process returned response {}", result.toString());
		return result.toString().trim();
	}
	
	private String parseResponse(String processResponse) {
		log.debug("Parsing response\n{}", processResponse);
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
		log.info("Could not parse response, returning null for country");
		return country;
	}
}
