package de.fraunhofer.iese.ids.ucapp.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.fraunhofer.iese.ids.ucapp.exception.UsagePermissionDeniedException;
import de.fraunhofer.iese.ids.ucapp.mydata.UsageControlPep;
import de.fraunhofer.iese.mydata.IMyDataEnvironment;
import de.fraunhofer.iese.mydata.policy.event.Event;
import de.fraunhofer.iese.mydata.policy.exception.EvaluationUndecidableException;
import de.fraunhofer.iese.mydata.policy.exception.InhibitException;
import de.fraunhofer.iese.mydata.util.MyDataUtil;

/**
 * @author Robin Brandstaedter <Robin.Brandstaedter@iese.fraunhofer.de>
 */
@Service
public class UsageControlEnforcementService {
  private static final Logger LOG = LoggerFactory.getLogger(UsageControlEnforcementService.class);

  private final UsageControlPep usageControlPep;
  IMyDataEnvironment myDataEnvironment;

  @Autowired
  public UsageControlEnforcementService(UsageControlPep usageControlPep, IMyDataEnvironment myDataEnvironment) {
    this.usageControlPep = usageControlPep;
    this.myDataEnvironment = myDataEnvironment;
  }

  public Object enforceUse(String targetDataUri, Object msgTarget, Object dataObject) throws UsagePermissionDeniedException {
    try {
      LOG.debug("enforceUse({}, {}, {})", targetDataUri, msgTarget, dataObject); // TODO maybe we want to remove this later?
      final Event event = MyDataUtil.checkedBlockingGet(usageControlPep.enforceUse(targetDataUri, msgTarget, dataObject));
      return event.getParameterValue("DataObject", Object.class);
    } catch (IOException e) {
      throw new UsagePermissionDeniedException("Communication problem, this will result in inhibition.", e);
    } catch (InhibitException e) {
      final String msg = e.getMessage();
      throw new UsagePermissionDeniedException("PDP decided to inhibit the usage" + (msg != null ? ": " + msg : "."), e);
    } catch (EvaluationUndecidableException e) {
      throw new UsagePermissionDeniedException("PDP reported that the evaluation is undecidable. This will result in inhibition.", e);
    } catch (RuntimeException e) {
      LOG.error("Exception in UsageControlEnforcementService::enforceUse", e);
      throw new UsagePermissionDeniedException("Internal error, have a look into the log files (Search-Term: \"UsageControlEnforcementService::enforceUse\"). For now: This will result in inhibition.");
    }
  }
}
