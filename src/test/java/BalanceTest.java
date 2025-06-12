import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceTest {

	private InvestmentAmount investmentAmount;
	private InvestPeriod investPeriod;

	@BeforeEach
	void setUp() {
		int amount = 1_000_000;
		investmentAmount = new MonthlyInvestmentAmount(amount);
		investPeriod = new MonthlyInvestPeriod(12);
	}

	@Test
	void created(){
		Balance balance = new CompoundBalance(investmentAmount, investPeriod);
		Assertions.assertNotNull(balance);
	}

	@Test
	void shouldReturnTotalPrincipal() {
		Balance balance = new CompoundBalance(investmentAmount, investPeriod);
		int totalPrincipal = balance.getTotalPrincipal();

		int expected = 12_000_000;
		Assertions.assertEquals(expected, totalPrincipal);
	}
}
