import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;

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
	void shouldThrowException_whenAmountIsNegative() {
		assertThrows(IllegalArgumentException.class, () -> new FixedDepositAmount(-1));
	}

	@Test
	void shouldReturnDepositAmount() {
		int depositAmount = investmentAmount.getDepositAmount();

		int expectedDepositAmount = 1_000_000;
		assertEquals(expectedDepositAmount, depositAmount);
	}

}
