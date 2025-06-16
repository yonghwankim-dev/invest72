import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

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

	@Test
	void created(){
		InvestPeriod investPeriod = new YearlyInvestPeriod(10);
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
	void shouldReturnTotalPrincipal(){
		InstallmentInvestmentAmount investmentAmount = new MonthlyInstallmentInvestmentAmount(1_000_000);
		InvestPeriod investPeriod = new YearlyInvestPeriod(10);

		int totalPrincipal = investPeriod.getTotalPrincipal(investmentAmount);

		int expectedTotalPrincipal = 120_000_000;
		assertEquals(expectedTotalPrincipal, totalPrincipal);
	}

	@ParameterizedTest
	@MethodSource(value = "currentMonthSource")
	void shouldReturnYearsInvested(int currentMonth, double expectedYearsInvested) {
		InvestPeriod investPeriod = new YearlyInvestPeriod(10);

		double yearsInvested = investPeriod.getYearsInvested(currentMonth);

		assertEquals(expectedYearsInvested, yearsInvested);
	}
}
