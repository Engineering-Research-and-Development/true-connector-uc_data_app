package de.fraunhofer.iese.ids.ucapp.exception;

public class UsagePermissionDeniedException extends Exception {
  private static final long serialVersionUID = -3742292313215570542L;

  public UsagePermissionDeniedException(String message) {
    super(message);
  }

  public UsagePermissionDeniedException(String message, Throwable cause) {
    super(message, cause);
  }

}
