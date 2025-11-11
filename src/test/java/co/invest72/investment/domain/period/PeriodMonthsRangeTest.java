package co.invest72.investment.domain.period;

import java.util.stream.Stream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PeriodMonthsRangeTest {

	public static Stream<Arguments> validMonths() {
		return Stream.of(
			Arguments.of(0),
			Arguments.of(1),
			Arguments.of(999),
			Arguments.of(11988)
		);
	}

	public static Stream<Arguments> invalidMonths() {
		return Stream.of(
			Arguments.of(-1),
			Arguments.of(11989)
		);
	}

	@ParameterizedTest
	@MethodSource(value = "validMonths")
	void newInstance(int months) {
		PeriodMonthsRange periodMonthsRange = new PeriodMonthsRange(months);

		Assertions.assertThat(periodMonthsRange).isNotNull();
	}

	@ParameterizedTest
	@MethodSource(value = "invalidMonths")
	void newInstance_whenInvalidMonths_thenThrowException(int months) {
		Assertions.assertThatThrownBy(() -> new PeriodMonthsRange(months))
			.isInstanceOf(IllegalArgumentException.class);
	}
}
