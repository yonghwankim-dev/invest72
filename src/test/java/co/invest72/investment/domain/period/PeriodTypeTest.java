package co.invest72.investment.domain.period;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PeriodTypeTest {

	public static Stream<Arguments> invalidPeriodTypeInputs() {
		return Stream.of(
			Arguments.of("일"),
			Arguments.of("invalid"),
			Arguments.of((Object)null),
			Arguments.of(""),
			Arguments.of(" ")
		);
	}

	@Test
	void shouldReturnMonth_whenInputIsMonth() {
		PeriodType periodType = PeriodType.from("월");
		assertEquals(PeriodType.MONTH, periodType);
	}

	@Test
	void shouldReturnYear_whenInputIsYear() {
		PeriodType periodType = PeriodType.from("년");
		assertEquals(PeriodType.YEAR, periodType);
	}

	@ParameterizedTest
	@MethodSource(value = "invalidPeriodTypeInputs")
	void shouldThrowException_whenInputIsInvalid(String input) {
		assertThrows(IllegalArgumentException.class, () -> {
			PeriodType.from(input);
		});
	}
}
