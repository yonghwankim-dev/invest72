import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AnnualInterestTest {

	private double annualRate;
	private double delta;

	@BeforeEach
	void setUp() {
		annualRate = 0.05;
		delta = 0.0001;
	}

	@Test
	void created(){
		Interest interest = new AnnualInterest(annualRate);
		Assertions.assertNotNull(interest);
	}

	@Test
	void shouldReturnAnnualRate_givenAnnualRateValue(){
		Interest interest = new AnnualInterest(annualRate);

		double actualAnnualRate = interest.getAnnualRate();

		double expectedAnnualRate = 0.05;
		Assertions.assertEquals(expectedAnnualRate, actualAnnualRate, delta);
	}

	@Test
	void shouldReturnMonthlyRate_givenAnnualRateValue(){
		Interest interest = new AnnualInterest(annualRate);

		double actualMonthlyRate = interest.getMonthlyRate();

		double expectedMonthlyRate = 0.05 / 12;
		Assertions.assertEquals(expectedMonthlyRate, actualMonthlyRate, delta);
	}
}
