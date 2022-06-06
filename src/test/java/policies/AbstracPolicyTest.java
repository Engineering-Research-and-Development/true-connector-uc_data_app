package policies;

import java.net.URI;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import de.fraunhofer.iais.eis.AbstractConstraint;
import de.fraunhofer.iais.eis.Action;
import de.fraunhofer.iais.eis.ContractAgreement;
import de.fraunhofer.iais.eis.ContractAgreementBuilder;
import de.fraunhofer.iais.eis.Permission;
import de.fraunhofer.iais.eis.PermissionBuilder;
import de.fraunhofer.iais.eis.ids.jsonld.Serializer;
import de.fraunhofer.iais.eis.util.TypedLiteral;
import de.fraunhofer.iais.eis.util.Util;

public abstract class AbstracPolicyTest {

	protected URI ARTIFACT = URI.create("http://w3id.org/engrd/connector/artifact/1");
	protected URI DATE_TIME_STAMP = URI.create("http://www.w3.org/2001/XMLSchema#dateTimeStamp");
	protected static DatatypeFactory datatypeFactory = null;
	protected static Serializer serializer;
	
	protected DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
	protected OffsetDateTime dateTime = OffsetDateTime.now(ZoneOffset.UTC);
	
	protected ContractAgreement createContractAgreement(List<AbstractConstraint> constraints) {
		Permission permission = new PermissionBuilder()
				._target_(ARTIFACT)
				._assignee_(Util.asList(URI.create("https://assignee.com")))
				._assigner_(Util.asList(URI.create("https://assigner.com")))
				._title_(Util.asList(new TypedLiteral("Permission title for artifact 1")))
				._action_(Util.asList(Action.USE))
				._description_(new TypedLiteral("Description of contract agreement"))
				._constraint_(constraints)
				.build();
		
		//@formatter:off
		return new ContractAgreementBuilder()
				._consumer_(URI.create("https://consumer.com"))
				._provider_(URI.create("https://provider.com"))
				._permission_(Util.asList(permission))
				._contractDate_(now())
				._contractStart_(now())
				.build();
		//@formatter:on
	}
	
	static {
		serializer =  new Serializer();
		try {
			datatypeFactory = DatatypeFactory.newInstance();
		} catch (DatatypeConfigurationException e) {
			throw new IllegalStateException("Error while trying to obtain a new instance of DatatypeFactory", e);
		}
	}
	
	protected static XMLGregorianCalendar now() {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTimeInMillis((new Date()).getTime());
		return datatypeFactory.newXMLGregorianCalendar(gc);
	}
}
