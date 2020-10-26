package de.fraunhofer.iese.ids.ucapp.service;

import org.springframework.stereotype.Service;

@Service
public class PurposeDeterminationService {
  // TODO just for demonstration
  private String purpose = "http://example.com/ids-purpose:Marketing";

  // TODO just for demonstration
  public void setPurpose(String purpose) {
    this.purpose = purpose;
  }

  public String determinePurpose(String msgTargetAppUri) {
    return purpose; // TODO implement me
  }
}
