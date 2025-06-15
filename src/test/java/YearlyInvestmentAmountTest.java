import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class YearlyInvestmentAmountTest {

	@Test
	void created(){
		InvestmentAmount investmentAmount = new YearlyInvestmentAmount(12_000_000);
		assertNotNull(investmentAmount);
	}

	@Test
	void shouldReturnAmount(){
		InvestmentAmount investmentAmount = new YearlyInvestmentAmount(12_000_000);

		int amount = investmentAmount.getAmount();

		int expectedAmount = 1_000_000;
		assertEquals(expectedAmount, amount);
	}
}
