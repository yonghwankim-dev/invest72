import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InvestmentAmountTest {

	private int monthlyAmount;

	@BeforeEach
	void setUp() {
		monthlyAmount = 1_000_000;
	}

	@Test
	void created(){
		InvestmentAmount investmentAmount = new MonthlyInstallmentInvestmentAmount(monthlyAmount);
		Assertions.assertNotNull(investmentAmount);
	}

	@Test
	void shouldReturnAmount(){
		InstallmentInvestmentAmount investmentAmount = new MonthlyInstallmentInvestmentAmount(monthlyAmount);

		int amount = investmentAmount.getMonthlyAmount();

		int expected = 1_000_000;
		Assertions.assertEquals(expected, amount);
	}

	@Test
	void shouldThrowException_whenAmountIsNegative() {
		monthlyAmount = -1_000_000;

		Assertions.assertThrows(IllegalArgumentException.class,
			() -> new MonthlyInstallmentInvestmentAmount(monthlyAmount));
	}
}
