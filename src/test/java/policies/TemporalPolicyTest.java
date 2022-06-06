package policies;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Test;

import de.fraunhofer.iais.eis.BinaryOperator;
import de.fraunhofer.iais.eis.Constraint;
import de.fraunhofer.iais.eis.ConstraintBuilder;
import de.fraunhofer.iais.eis.ContractAgreement;
import de.fraunhofer.iais.eis.LeftOperand;
import de.fraunhofer.iais.eis.util.RdfResource;
import de.fraunhofer.iais.eis.util.Util;

public class TemporalPolicyTest extends AbstracPolicyTest {

	@Test
	public void createRestrictAccessIntervalPolicy() throws IOException {
		ContractAgreement ca = createAgreement();
		assertNotNull(ca);
//		System.out.println(serializer.serialize(ca));
	}

	private ContractAgreement createAgreement() {
		Constraint after = new ConstraintBuilder()
				._leftOperand_(LeftOperand.POLICY_EVALUATION_TIME)
				._operator_(BinaryOperator.AFTER)
				._rightOperand_(new RdfResource(dateTime.minusDays(7).format(formatter), DATE_TIME_STAMP))
				.build();

		Constraint before = new ConstraintBuilder()
				._leftOperand_(LeftOperand.POLICY_EVALUATION_TIME)
				._operator_(BinaryOperator.BEFORE)
				._rightOperand_(new RdfResource(dateTime.plusMonths(1).format(formatter), DATE_TIME_STAMP))
				.build();
		
		return createContractAgreement(Util.asList(after, before));
	}

}
