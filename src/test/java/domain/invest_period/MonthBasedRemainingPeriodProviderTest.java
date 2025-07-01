package domain.invest_period;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MonthBasedRemainingPeriodProviderTest {

	@Test
	void created() {
		PeriodRange periodRange = new PeriodMonthsRange(12);
		RemainingPeriodProvider remainingPeriodProvider = new MonthBasedRemainingPeriodProvider(periodRange);
		assertNotNull(remainingPeriodProvider);
	}

	@Test
	void shouldReturnRemainingPeriodInYears_whenPeriodRangeIs12Month() {
		PeriodRange periodRange = new PeriodMonthsRange(12);
		RemainingPeriodProvider remainingPeriodProvider = new MonthBasedRemainingPeriodProvider(periodRange);
		double remainingPeriod = remainingPeriodProvider.calRemainingPeriodInYears(0);

		double expectedRemainingPeriod = 1.0;
		assertEquals(expectedRemainingPeriod, remainingPeriod);
	}
}
