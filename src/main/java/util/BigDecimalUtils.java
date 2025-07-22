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

	public static BigDecimal pow(BigDecimal base, int exponent) {
		if (exponent < 0) {
			throw new IllegalArgumentException("Exponent must be non-negative.");
		}
		return round(base.pow(exponent));
	}

	public static BigDecimal round(BigDecimal value) {
		return value.setScale(SCALE, ROUNDING_MODE);
	}

	public static BigDecimal multiply(BigDecimal amountDecimal, BigDecimal monthlyRate) {
		if (amountDecimal == null || monthlyRate == null) {
			throw new IllegalArgumentException("Arguments must not be null.");
		}
		return round(amountDecimal.multiply(monthlyRate));
	}
}
