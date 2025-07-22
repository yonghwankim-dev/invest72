package domain.interest_rate;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import util.BigDecimalUtils;

class AnnualInterestRateTest {

	private double delta;
	private InterestRate interestRate;

	private final BigDecimal deltaBigDecimal = BigDecimal.valueOf(0.000001);

	private void assertBigDecimalEquals(BigDecimal expected, BigDecimal actual) {
		BigDecimal diff = expected.subtract(actual).abs();
		Assertions.assertTrue(diff.compareTo(deltaBigDecimal) <= 0,
			() -> "Expected: " + expected + ", but was: " + actual + ", difference: " + diff);
	}

	@BeforeEach
	void setUp() {
		double annualRate = 0.05;
		delta = 0.000001;
		interestRate = new AnnualInterestRate(annualRate);
	}

	@Test
	void shouldReturnAnnualRate_givenAnnualRateValue() {
		BigDecimal annualRate = interestRate.getAnnualRate();

		BigDecimal expectedAnnualRate = BigDecimalUtils.valueOf(0.05);
		Assertions.assertEquals(expectedAnnualRate, annualRate);
	}

	@Test
	void shouldReturnMonthlyRate_givenAnnualRateValue() {
		BigDecimal actualMonthlyRate = interestRate.getMonthlyRate();

		BigDecimal expectedMonthlyRate = BigDecimalUtils.valueOf(0.004167);
		assertBigDecimalEquals(expectedMonthlyRate, actualMonthlyRate);
	}

	@Test
	void shouldThrowException_whenAnnualRateIsNegative() {
		Assertions.assertThrows(IllegalArgumentException.class,
			() -> new AnnualInterestRate(-0.01));
	}

	@Test
	void shouldThrowException_whenInterestRateEqualMoreThan100Percent() {
		assertThrows(IllegalArgumentException.class, () -> new AnnualInterestRate(1.0));
	}

	@Test
	void shouldReturnGrowthFactor() {
		assertGrowthFactor(1, 1.000000);
		assertGrowthFactor(2, 1.004167);
		assertGrowthFactor(3, 1.008351);
	}

	private void assertGrowthFactor(int month, double expected) {
		Assertions.assertEquals(expected, interestRate.calGrowthFactor(month), delta);
	}

	@Test
	void shouldReturnMonthlyInterest() {
		int amount = 1_000_000;

		BigDecimal actualMonthlyInterest = interestRate.calMonthlyInterest(amount);

		BigDecimal expectedMonthlyInterest = BigDecimal.valueOf(4167);
		assertBigDecimalEquals(expectedMonthlyInterest, actualMonthlyInterest);
	}
}
