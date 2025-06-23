package invest_period;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import invest_amount.InstallmentInvestmentAmount;
import invest_amount.MonthlyInstallmentInvestmentAmount;
import invest_period.InvestPeriod;
import invest_period.MonthlyInvestPeriod;

class MonthlyInvestPeriodTest {

	private InvestPeriod investPeriod;

	@BeforeEach
	void setUp() {
		investPeriod = new MonthlyInvestPeriod(12);
	}

	@Test
	void created(){
		Assertions.assertNotNull(investPeriod);
	}

	@Test
	void shouldCreated_whenMonthsIsZero(){
		investPeriod = new MonthlyInvestPeriod(0);
		Assertions.assertNotNull(investPeriod);
	}

	@Test
	void shouldReturnMonths() {
		int actualMonths = investPeriod.getMonths();

		int expectedMonths = 12;
		Assertions.assertEquals(expectedMonths, actualMonths);
	}

	@Test
	void shouldThrowException_whenInvestmentPeriodIsNegative(){
		Assertions.assertThrows(IllegalArgumentException.class,
			() -> new MonthlyInvestPeriod(-1));
	}


	@Test
	void shouldThrowException_whenInvestmentPeriodGreaterThan999(){
		Assertions.assertThrows(IllegalArgumentException.class,
			() -> new MonthlyInvestPeriod(1000));
	}

	@Test
	void shouldReturnTotalPrincipal_givenMonthlyInvestment() {
		investPeriod = new MonthlyInvestPeriod(12);
		InstallmentInvestmentAmount investmentAmount = new MonthlyInstallmentInvestmentAmount(1_000_000);

		int totalPrincipal = investPeriod.getTotalPrincipal(investmentAmount);

		int expected = 12_000_000;
		Assertions.assertEquals(expected, totalPrincipal);
	}

	@Test
	void shouldThrowException_whenCurrentMonthIsNegative() {
		Assertions.assertThrows(IllegalArgumentException.class,
			() -> investPeriod.getRemainingPeriodInYears(-1));
	}

	@Test
	void shouldThrowException_whenCurrentMonthIsGreaterThanMonths() {
		Assertions.assertThrows(IllegalArgumentException.class,
			() -> investPeriod.getRemainingPeriodInYears(13));
	}
}
