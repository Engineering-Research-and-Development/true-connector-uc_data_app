package de.fraunhofer.iese.ids.ucapp.controller;

import de.fraunhofer.iese.ids.ucapp.service.PurposeDeterminationService;
import de.fraunhofer.iese.ids.ucapp.service.SpatialService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Robin Brandstaedter <Robin.Brandstaedter@iese.fraunhofer.de>
 */
@Controller
@RequestMapping(path = "demo/set")
public class DemoConfigController {
  //TODO just for demonstration, remove later
  private final static Logger LOG = LoggerFactory.getLogger(DemoConfigController.class);

  private final SpatialService spatialService;
  private final PurposeDeterminationService purposeDeterminationService;

  @Autowired
  public DemoConfigController(SpatialService spatialService, PurposeDeterminationService purposeDeterminationService) {
    this.spatialService = spatialService;
    this.purposeDeterminationService = purposeDeterminationService;
  }

  @PutMapping(value = "/spatial", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public void setSpatial(@RequestBody String spatial) {
    this.spatialService.setSpatial(spatial.replaceAll("\"", ""));
  }

  @PutMapping(value = "/purpose", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public void setPurpose(@RequestBody String purpose) {
    this.purposeDeterminationService.setPurpose(purpose.replaceAll("\"", ""));
  }
}
