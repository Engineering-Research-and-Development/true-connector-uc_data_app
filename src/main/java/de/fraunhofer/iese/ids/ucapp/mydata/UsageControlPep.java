package de.fraunhofer.iese.ids.ucapp.mydata;


import de.fraunhofer.iese.mydata.policy.event.Event;
import de.fraunhofer.iese.mydata.reactive.common.EventParameter;
import de.fraunhofer.iese.mydata.reactive.common.EventSpecification;
import de.fraunhofer.iese.mydata.reactive.common.Modifiers;
import de.fraunhofer.iese.mydata.reactive.common.PepServiceDescription;
import rx.Observable;

@PepServiceDescription(componentName = "usage-control-pep")
@Modifiers
public interface UsageControlPep {
  @EventSpecification(action = "use")
  Observable<Event> enforceUse(
      @EventParameter(name = "TargetDataUri") String targetDataUri,
      @EventParameter(name = "MsgTarget") Object msgTarget,
      @EventParameter(name = "DataObject") Object dataObject
  );
}
