import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BalanceTest {
	@Test
	void created(){
		int amount = 1_000_000;
		InvestmentAmount investmentAmount = new MonthlyInvestmentAmount(amount);
		Balance balance = new CompoundBalance(investmentAmount);
		Assertions.assertNotNull(balance);
	}

	@Test
	void shouldReturnTotalPrincipal() {
		int amount = 1_000_000;
		InvestmentAmount investmentAmount = new MonthlyInvestmentAmount(amount);
		Balance balance = new CompoundBalance(investmentAmount);
		int totalPrincipal = balance.getTotalPrincipal();

		int expected = 12_000_000;
		Assertions.assertEquals(expected, totalPrincipal);
	}
}
