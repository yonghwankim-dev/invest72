import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FixedDepositAmountTest {

	private LumpSumInvestmentAmount investmentAmount;

	@BeforeEach
	void setUp() {
		investmentAmount = new FixedDepositAmount(1_000_000);
	}

	@Test
	void created(){
		assertNotNull(investmentAmount);
	}

	@Test
	void shouldReturnDepositAmount() {
		int depositAmount = investmentAmount.getDepositAmount();

		int expectedDepositAmount = 1_000_000;
		assertEquals(expectedDepositAmount, depositAmount);
	}

}
