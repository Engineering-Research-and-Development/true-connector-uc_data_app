package de.fraunhofer.iese.ids.ucapp.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.fraunhofer.iese.ids.ucapp.exception.UsagePermissionDeniedException;
import de.fraunhofer.iese.ids.ucapp.model.IdsUseObject;
import de.fraunhofer.iese.ids.ucapp.service.UsageControlEnforcementService;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Robin Brandstaedter <Robin.Brandstaedter@iese.fraunhofer.de>
 */
@RestController
@RequestMapping(path = "enforce/usage")
public class EnforceUsageController {
  private final static Logger LOG = LoggerFactory.getLogger(EnforceUsageController.class);
  private final UsageControlEnforcementService usageControlEnforcementService;

  @Autowired
  public EnforceUsageController(UsageControlEnforcementService usageControlEnforcementService) {
    this.usageControlEnforcementService = usageControlEnforcementService;
  }

  @PostMapping(value = "/use", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<Object> usageControlUse(@RequestBody IdsUseObject idsUseObject) {
    try {
      return ResponseEntity.ok(usageControlEnforcementService.enforceUse(idsUseObject.getTargetDataUri(), idsUseObject.getMsgTarget(), idsUseObject.getDataObject()));
    } catch (UsagePermissionDeniedException e) {
      LOG.info("Usage of data {} has been denied. Reason: {}", idsUseObject.getTargetDataUri(), e.getMessage());
      Map<String, String> map = new HashMap<String,String>();
      map.put("message", e.getMessage());
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(map);
    }
  }
}
