package co.invest72.investment.domain.period;

import java.util.stream.Stream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PeriodYearRangeTest {

	public static Stream<Arguments> invalidYears() {
		return Stream.of(
			Arguments.of(-1),
			Arguments.of(1000)
		);
	}

	@Test
	void newInstance_whenValidYears() {
		PeriodYearRange periodYearRange = new PeriodYearRange(5);

		Assertions.assertThat(periodYearRange).isNotNull();
	}

	@ParameterizedTest
	@MethodSource(value = "invalidYears")
	void newInstance_whenInvalidYears_thenThrowException(int years) {
		Assertions.assertThatThrownBy(() -> new PeriodYearRange(years))
			.isInstanceOf(IllegalArgumentException.class);
	}
}
