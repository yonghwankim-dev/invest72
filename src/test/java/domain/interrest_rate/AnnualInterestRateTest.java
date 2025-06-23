package domain.interrest_rate;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.interest_rate.AnnualInterestRate;
import domain.interest_rate.InterestRate;

class AnnualInterestRateTest {

	private double annualRate;
	private double delta;

	@BeforeEach
	void setUp() {
		annualRate = 0.05;
		delta = 0.0001;
	}

	@Test
	void created(){
		InterestRate interestRate = new AnnualInterestRate(annualRate);
		Assertions.assertNotNull(interestRate);
	}

	@Test
	void shouldReturnAnnualRate_givenAnnualRateValue(){
		InterestRate interestRate = new AnnualInterestRate(annualRate);

		double actualAnnualRate = interestRate.getAnnualRate();

		double expectedAnnualRate = 0.05;
		Assertions.assertEquals(expectedAnnualRate, actualAnnualRate, delta);
	}

	@Test
	void shouldReturnMonthlyRate_givenAnnualRateValue(){
		InterestRate interestRate = new AnnualInterestRate(annualRate);

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
}
