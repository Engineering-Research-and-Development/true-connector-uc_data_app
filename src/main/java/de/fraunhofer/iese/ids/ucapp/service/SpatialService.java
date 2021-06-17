package de.fraunhofer.iese.ids.ucapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.eng.idsa.countryprovider.CountryProvider;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SpatialService {

	@Autowired
	private CountryProvider countryProvider;

	private String spatial;

	public String getSpatial() {
		if (spatial == null) {
			log.info("Calling countryProvider service");
			spatial = countryProvider.getCountry();
		}
		return spatial; 
	}

	// TODO just for demonstration
	public void setSpatial(String spatial) {
		this.spatial = spatial;
	}
}
