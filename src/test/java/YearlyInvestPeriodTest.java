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
}
