package domain.invest_period;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MonthBasedRemainingPeriodProviderTest {

	@Test
	void created() {
		RemainingPeriodProvider remainingPeriodProvider = new MonthBasedRemainingPeriodProvider();
		assertNotNull(remainingPeriodProvider);
	}

}
