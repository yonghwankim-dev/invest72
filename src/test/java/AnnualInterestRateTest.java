import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
}
