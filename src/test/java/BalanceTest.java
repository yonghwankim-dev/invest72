import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceTest {

	private Balance balance;

	@BeforeEach
	void setUp() {
		int amount = 1_000_000;
		InvestmentAmount investmentAmount = new MonthlyInvestmentAmount(amount);
		InvestPeriod investPeriod = new MonthlyInvestPeriod(12);
		InterestRate interestRate = new AnnualInterestRate(0.05);
		balance = new CompoundBalance(investmentAmount, investPeriod, interestRate);
	}

	@Test
	void created(){
		Assertions.assertNotNull(balance);
	}

	@Test
	void shouldReturnTotalPrincipal() {
		int totalPrincipal = balance.getTotalPrincipal();

		int expected = 12_000_000;
		Assertions.assertEquals(expected, totalPrincipal);
	}

	@Test
	void shouldReturnInterestAmount() {
		int interestAmount = balance.getInterestAmount();

		int expected = 330_017; // Assuming no interest for the initial test
		Assertions.assertEquals(expected, interestAmount);
	}
}
