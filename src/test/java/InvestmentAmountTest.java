import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class InvestmentAmountTest {
	@Test
	void created(){
		int monthlyAmount = 1_000_000;
		InvestmentAmount investmentAmount = new MonthlyInvestmentAmount(monthlyAmount);
		Assertions.assertNotNull(investmentAmount);
	}

	@Test
	void shouldReturnAmount(){
		int monthlyAmount = 1_000_000;
		InvestmentAmount investmentAmount = new MonthlyInvestmentAmount(monthlyAmount);

		int amount = investmentAmount.getAmount();

		int expected = 1_000_000;
		Assertions.assertEquals(expected, amount);
	}
}
