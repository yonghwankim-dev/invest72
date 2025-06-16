import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FixedDepositAmountTest {

	@Test
	void created(){
		LumpSumInvestmentAmount investmentAmount = new FixedDepositAmount(1_000_000);
		assertNotNull(investmentAmount);
	}

	@Test
	void shouldReturnDepositAmount() {
		LumpSumInvestmentAmount investmentAmount = new FixedDepositAmount(1_000_000);

		int depositAmount = investmentAmount.getDepositAmount();

		int expectedDepositAmount = 1_000_000;
		assertEquals(expectedDepositAmount, depositAmount);
	}

}
