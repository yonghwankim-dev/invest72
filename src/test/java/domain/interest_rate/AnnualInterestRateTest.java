package domain.interest_rate;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AnnualInterestRateTest {

	private double annualRate;
	private double delta;
	private InterestRate interestRate;

	@BeforeEach
	void setUp() {
		annualRate = 0.05;
		delta = 0.000001;
		interestRate = new AnnualInterestRate(annualRate);
	}

	@Test
	void shouldReturnAnnualRate_givenAnnualRateValue() {
		double actualAnnualRate = interestRate.getAnnualRate();

		double expectedAnnualRate = 0.05;
		Assertions.assertEquals(expectedAnnualRate, actualAnnualRate, delta);
	}

	@Test
	void shouldReturnMonthlyRate_givenAnnualRateValue() {
		double actualMonthlyRate = interestRate.getMonthlyRate();

		double expectedMonthlyRate = 0.05 / 12;
		Assertions.assertEquals(expectedMonthlyRate, actualMonthlyRate, delta);
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
		int expected = 1;
		int month = 1;

		Assertions.assertEquals(expected, interestRate.calGrowthFactor(month), delta);
		Assertions.assertEquals(1.004166, interestRate.calGrowthFactor(2), delta);
		Assertions.assertEquals(1.008350, interestRate.calGrowthFactor(3), delta);
	}
}
