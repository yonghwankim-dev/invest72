package domain.invest_period;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.invest_amount.InstallmentInvestmentAmount;
import domain.invest_amount.MonthlyInstallmentInvestmentAmount;

class MonthlyInvestPeriodTest {

	private InvestPeriod investPeriod;

	@BeforeEach
	void setUp() {
		investPeriod = new MonthlyInvestPeriod(12);
	}

	@Test
	void created() {
		assertNotNull(investPeriod);
	}

	@Test
	void shouldCreated_whenMonthsIsZero() {
		investPeriod = new MonthlyInvestPeriod(0);
		assertNotNull(investPeriod);
	}

	@Test
	void shouldReturnMonths() {
		int actualMonths = investPeriod.getMonths();

		int expectedMonths = 12;
		assertEquals(expectedMonths, actualMonths);
	}

	@Test
	void shouldThrowException_whenInvestmentPeriodIsNegative() {
		assertThrows(IllegalArgumentException.class,
			() -> new MonthlyInvestPeriod(-1));
	}

	@Test
	void shouldThrowException_whenInvestmentPeriodGreaterThan999() {
		assertThrows(IllegalArgumentException.class,
			() -> new MonthlyInvestPeriod(1000));
	}

	@Test
	void shouldReturnTotalPrincipal_givenMonthlyInvestment() {
		investPeriod = new MonthlyInvestPeriod(12);
		InstallmentInvestmentAmount investmentAmount = new MonthlyInstallmentInvestmentAmount(1_000_000);

		int totalPrincipal = investPeriod.getTotalPrincipal(investmentAmount);

		int expected = 12_000_000;
		assertEquals(expected, totalPrincipal);
	}
	
	@Test
	void shouldThrowException_whenCurrentMonthIsGreaterThanMonths() {
		assertThrows(IllegalArgumentException.class,
			() -> investPeriod.getRemainingPeriodInYears(13));
	}

	@Test
	void shouldReturnMonths_whenPeriodRangeInstanceOfPeriodYearRange() {
		PeriodRange periodRange = new PeriodYearRange(1);
		investPeriod = new MonthlyInvestPeriod(periodRange);

		int months = investPeriod.getMonths();
		double remainingPeriodInYears = investPeriod.getRemainingPeriodInYears(1);

		assertInstanceOf(MonthlyInvestPeriod.class, investPeriod);
		assertEquals(12, months);
		assertEquals(0.91, remainingPeriodInYears, 0.01);
	}

	@Test
	void shouldReturnRemainingPeriodInYears_whenPeriodYearRangeType() {
		PeriodRange periodRange = new PeriodYearRange(1);
		investPeriod = new MonthlyInvestPeriod(periodRange);

		double remainingPeriodInYears = investPeriod.getRemainingPeriodInYears(1);

		assertInstanceOf(MonthlyInvestPeriod.class, investPeriod);
		assertEquals(0.91, remainingPeriodInYears, 0.01);
	}
}
