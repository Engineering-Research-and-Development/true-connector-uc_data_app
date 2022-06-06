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

public class RestrictAccessPurposeTest extends AbstracPolicyTest {
	
	@Test
	public void createRestrictAccessPurposeAgreementTest() throws IOException {
		ContractAgreement ca = createAgreement();
		assertNotNull(ca);
//		System.out.println(serializer.serialize(ca));
	}

	private ContractAgreement createAgreement() {
		Constraint c = new ConstraintBuilder()
				._leftOperand_(LeftOperand.PURPOSE)
				._operator_(BinaryOperator.EQ)
				._rightOperand_(new RdfResource("http://example.com/ids-purpose:Marketing", URI.create("anyURI")))
				._pipEndpoint_(URI.create("http://pip.endpoint.purpose"))
				.build();
		return createContractAgreement(Util.asList(c));
		
	}
}
