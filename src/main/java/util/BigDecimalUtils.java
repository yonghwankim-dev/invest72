package util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalUtils {
	private static final int SCALE = 6;
	private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;

	private BigDecimalUtils() {
		throw new UnsupportedOperationException("Utility class cannot be instantiated.");
	}

	public static BigDecimal divide(BigDecimal dividend, BigDecimal divisor) {
		if (divisor.compareTo(BigDecimal.ZERO) == 0) {
			throw new ArithmeticException("Division by zero is not allowed.");
		}
		return dividend.divide(divisor, SCALE, ROUNDING_MODE);
	}

	public static BigDecimal valueOf(double value) {
		return BigDecimal.valueOf(value).setScale(SCALE, ROUNDING_MODE);
	}
}
