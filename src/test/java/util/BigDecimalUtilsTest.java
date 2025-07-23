package util;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BigDecimalUtilsTest {
	private final BigDecimal deltaBigDecimal = BigDecimal.valueOf(0.000001);

	private void assertBigDecimalEquals(BigDecimal expected, BigDecimal actual) {
		BigDecimal diff = expected.subtract(actual).abs();
		Assertions.assertTrue(diff.compareTo(deltaBigDecimal) <= 0,
			() -> "Expected: " + expected + ", but was: " + actual + ", difference: " + diff);
	}

	@Test
	void testValueOf() {
		double value = 1.0511660;
		BigDecimal actual = BigDecimal.valueOf(value);

		BigDecimal expected = BigDecimal.valueOf(1.051166);
		assertBigDecimalEquals(expected, actual);
	}

}
