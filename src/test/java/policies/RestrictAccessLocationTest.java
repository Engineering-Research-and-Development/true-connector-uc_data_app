package policies;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.net.URI;

import org.junit.Test;

import de.fraunhofer.iais.eis.BinaryOperator;
import de.fraunhofer.iais.eis.Constraint;
import de.fraunhofer.iais.eis.ConstraintBuilder;
import de.fraunhofer.iais.eis.ContractAgreement;
import de.fraunhofer.iais.eis.LeftOperand;
import de.fraunhofer.iais.eis.util.RdfResource;
import de.fraunhofer.iais.eis.util.Util;

public class RestrictAccessLocationTest extends AbstracPolicyTest {
	
	@Test
	public void createRestrictAccessLocationAgreementTest() throws IOException {
		ContractAgreement ca = createAgreement();
		assertNotNull(ca);
//		System.out.println(serializer.serialize(ca));
	}

	private ContractAgreement createAgreement() {
		Constraint c = new ConstraintBuilder()
				._leftOperand_(LeftOperand.ABSOLUTE_SPATIAL_POSITION)
				._operator_(BinaryOperator.EQ)
				._rightOperand_(new RdfResource("RS", URI.create("anyURI")))
				._pipEndpoint_(URI.create("http://pip.endpoint.location"))
				.build();
		return createContractAgreement(Util.asList(c));
		
	}
}
