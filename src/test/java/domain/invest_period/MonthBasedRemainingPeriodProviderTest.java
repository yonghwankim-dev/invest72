package domain.invest_period;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class MonthBasedRemainingPeriodProviderTest {

	private RemainingPeriodProvider remainingPeriodProvider;

	public static Stream<Arguments> currentMonthSource() {
		return Stream.of(
			Arguments.of(0, 1.0), // 12 months remaining
			Arguments.of(1, 0.91) // 11 months remaining
			, Arguments.of(2, 0.83) // 10 months remaining
			, Arguments.of(3, 0.75) // 9 months remaining
			, Arguments.of(6, 0.5) // 6 months remaining
			, Arguments.of(7, 0.42) // 5 months remainin
			, Arguments.of(8, 0.33)// 4 months remaining
			, Arguments.of(9, 0.25) // 3 months remaining
			, Arguments.of(11, 0.08) // 1 month remaining
			, Arguments.of(12, 0.0) // 0 months remaining
		);
	}

	@BeforeEach
	void setUp() {
		PeriodRange periodRange = new PeriodMonthsRange(12);
		remainingPeriodProvider = new MonthBasedRemainingPeriodProvider(periodRange);
	}

	@Test
	void created() {
		assertNotNull(remainingPeriodProvider);
	}

	@ParameterizedTest
	@MethodSource(value = "currentMonthSource")
	void shouldReturnRemainingPeriodInYears_givenCurrentMonth(int currentMonth, double expectedRemainingPeriod) {
		double remainingPeriod = remainingPeriodProvider.calRemainingPeriodInYears(currentMonth);

		assertEquals(expectedRemainingPeriod, remainingPeriod, 0.01);
	}
}
