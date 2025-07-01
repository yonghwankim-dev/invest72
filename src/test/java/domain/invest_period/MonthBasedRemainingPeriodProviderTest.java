package domain.invest_period;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MonthBasedRemainingPeriodProviderTest {

	@Test
	void created() {
		RemainingPeriodProvider remainingPeriodProvider = new MonthBasedRemainingPeriodProvider();
		assertNotNull(remainingPeriodProvider);
	}

	@Test
	void shouldReturnRemainingPeriodInYears_whenPeriodRangeIs12Month() {
		RemainingPeriodProvider remainingPeriodProvider = new MonthBasedRemainingPeriodProvider();
		double remainingPeriod = remainingPeriodProvider.calRemainingPeriodInYears(0);

		double expectedRemainingPeriod = 1.0;
		assertEquals(expectedRemainingPeriod, remainingPeriod);
	}
}
