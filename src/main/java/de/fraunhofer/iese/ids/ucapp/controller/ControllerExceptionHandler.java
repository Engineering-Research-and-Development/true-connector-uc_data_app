package de.fraunhofer.iese.ids.ucapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author Robin Brandstädter (Robin.Brandstaedter@iese.fraunhofer.de)
 */
@ControllerAdvice
public class ControllerExceptionHandler {
  private final static Logger LOG = LoggerFactory.getLogger(ControllerExceptionHandler.class);

  @ExceptionHandler(Exception.class)
  public final ResponseEntity<?> handleException(Exception ex) {
    LOG.error("Uncatched Exception", ex);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
  }
}
