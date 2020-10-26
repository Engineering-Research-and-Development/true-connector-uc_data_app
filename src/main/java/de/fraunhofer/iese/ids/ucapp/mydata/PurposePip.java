package de.fraunhofer.iese.ids.ucapp.mydata;

import de.fraunhofer.iese.ids.ucapp.service.PurposeDeterminationService;
import de.fraunhofer.iese.mydata.registry.ActionDescription;
import de.fraunhofer.iese.mydata.registry.ActionParameterDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PurposePip {
  private static final Logger LOG = LoggerFactory.getLogger(PurposePip.class);

  private final PurposeDeterminationService purposeDeterminationService;

  @Autowired
  public PurposePip(PurposeDeterminationService purposeDeterminationService) {
    this.purposeDeterminationService = purposeDeterminationService;
  }

  @ActionDescription(methodName = "purpose")
  public boolean purpose(
	  @ActionParameterDescription(name = "purpose-uri", mandatory = true) String purposeUri,
      @ActionParameterDescription(name = "MsgTargetAppUri", mandatory = true) String msgTargetAppUri
  ) {
    final String purpose = purposeDeterminationService.determinePurpose(msgTargetAppUri);
    LOG.debug("purpose pip called mit MsgTargetAppUri={}, returning: {}", msgTargetAppUri, purpose);
    return purposeUri.equalsIgnoreCase(purpose);
  }
}
