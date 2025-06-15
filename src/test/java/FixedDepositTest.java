import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FixedDepositTest {
	@Test
	void created(){
		Investment investment = new FixedDeposit();
		assertNotNull(investment);
	}

	@Test
	void shouldReturnAmount(){
		Investment investment = new FixedDeposit();

		int amount = investment.getAmount();

		int expectedAmount = 1_051_162;
		assertEquals(expectedAmount, amount);
	}
}
