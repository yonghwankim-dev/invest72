package invest_amount;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import invest_amount.InstallmentInvestmentAmount;
import invest_amount.MonthlyInstallmentInvestmentAmount;

class InvestmentAmountTest {

	private InstallmentInvestmentAmount investmentAmount;

	@BeforeEach
	void setUp() {
		investmentAmount = new MonthlyInstallmentInvestmentAmount(1_000_000);
	}

	@Test
	void created(){
		Assertions.assertNotNull(investmentAmount);
	}

	@Test
	void shouldReturnAmount(){
		int amount = investmentAmount.getMonthlyAmount();

		int expected = 1_000_000;
		Assertions.assertEquals(expected, amount);
	}

	@Test
	void shouldThrowException_whenAmountIsNegative() {
		Assertions.assertThrows(IllegalArgumentException.class,
			() -> new MonthlyInstallmentInvestmentAmount(-1_000_000));
	}
}
