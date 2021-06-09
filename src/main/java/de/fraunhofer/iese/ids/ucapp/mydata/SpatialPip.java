package de.fraunhofer.iese.ids.ucapp.mydata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import de.fraunhofer.iese.ids.ucapp.service.SpatialService;
import de.fraunhofer.iese.mydata.pip.PipService;
import de.fraunhofer.iese.mydata.registry.ActionDescription;
import de.fraunhofer.iese.mydata.registry.ActionParameterDescription;

@PipService(componentName = "spatial-pip")
public class SpatialPip {
  private static final Logger LOG = LoggerFactory.getLogger(SpatialPip.class);

  private final SpatialService spatialService;

  @Autowired
  public SpatialPip(SpatialService spatialService) {
    this.spatialService = spatialService;
  }

  @ActionDescription(methodName = "absoluteSpatialPosition")
  public boolean spatial(@ActionParameterDescription(name = "absoluteSpatialPosition-uri")String absoluteSpatialPosition) {
    final String spatial = spatialService.getSpatial();
    LOG.debug("spatial pip called, returning: {}", spatial);
    return spatial.equalsIgnoreCase(absoluteSpatialPosition);
  }
}
