package domain.invest_period;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import domain.invest_amount.InstallmentInvestmentAmount;
import domain.invest_amount.MonthlyInstallmentInvestmentAmount;

class YearlyInvestPeriodTest {

	public static Stream<Arguments> invalidYears() {
		return Stream.of(
			Arguments.of(-1),
			Arguments.of(-10),
			Arguments.of(1000)
		);
	}

	public static Stream<Arguments> currentMonthSource() {
		return Stream.of(
			Arguments.of(0, 10.0),
			Arguments.of(120, 0)
		);
	}

	public static Stream<Arguments> yearsSource() {
		return Stream.of(
			Arguments.of(0),
			Arguments.of(1),
			Arguments.of(999)
		);
	}

	@ParameterizedTest
	@MethodSource(value = "yearsSource")
	void created(int years) {
		InvestPeriod investPeriod = new YearlyInvestPeriod(years);
		assertNotNull(investPeriod);
	}

	@ParameterizedTest
	@MethodSource(value = "invalidYears")
	void shouldThrowException_whenInvalidYears(int years) {
		assertThrows(IllegalArgumentException.class, () -> new YearlyInvestPeriod(years));
	}

	@Test
	void shouldReturnMonths() {
		InvestPeriod investPeriod = new YearlyInvestPeriod(10);

		int months = investPeriod.getMonths();

		int expectedMonths = 120;
		assertEquals(expectedMonths, months);
	}

	@Test
	void shouldReturnTotalPrincipal() {
		InstallmentInvestmentAmount investmentAmount = new MonthlyInstallmentInvestmentAmount(1_000_000);
		InvestPeriod investPeriod = new YearlyInvestPeriod(10);

		int totalPrincipal = investPeriod.getTotalPrincipal(investmentAmount);

		int expectedTotalPrincipal = 120_000_000;
		assertEquals(expectedTotalPrincipal, totalPrincipal);
	}
}
