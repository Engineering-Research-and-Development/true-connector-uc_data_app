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

public class ComplexContraintPolicyTest extends AbstracPolicyTest {
	
	@Test
	public void createComplexContraintPolicyAgreementTest() throws IOException {
		ContractAgreement complex = createComplexAgreement();
		assertNotNull(complex);
//		System.out.println(serializer.serialize(complex));
	}

	private ContractAgreement createComplexAgreement() {
		Constraint purpose = new ConstraintBuilder()
				._leftOperand_(LeftOperand.PURPOSE)
				._operator_(BinaryOperator.EQ)
				._rightOperand_(new RdfResource("http://example.com/ids-purpose:Marketing", URI.create("anyURI")))
				._pipEndpoint_(URI.create("http://pip.endpoint.purpose"))
				.build();
		
		Constraint location = new ConstraintBuilder()
				._leftOperand_(LeftOperand.ABSOLUTE_SPATIAL_POSITION)
				._operator_(BinaryOperator.EQ)
				._rightOperand_(new RdfResource("RS", URI.create("anyURI")))
				._pipEndpoint_(URI.create("http://pip.endpoint.location"))
				.build();
		
		Constraint before = new ConstraintBuilder()
				._leftOperand_(LeftOperand.POLICY_EVALUATION_TIME)
				._operator_(BinaryOperator.BEFORE)
				._rightOperand_(new RdfResource(dateTime.plusMonths(1).format(formatter), DATE_TIME_STAMP))
				.build();
		
		return createContractAgreement(Util.asList(before, location, purpose));
		
	}
}
