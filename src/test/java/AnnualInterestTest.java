import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AnnualInterestTest {

	@Test
	void created(){
		double annualRate = 0.0;
		Interest interest = new AnnualInterest(annualRate);
		Assertions.assertNotNull(interest);
	}

	@Test
	void shouldReturnMonthlyRate_givenAnnualRateValue(){
		double annualRate = 0.05;
		Interest interest = new AnnualInterest(annualRate);

		double actualAnnualRate = interest.getAnnualRate();

		double expectedAnnualRate = 0.05;
		Assertions.assertEquals(expectedAnnualRate, actualAnnualRate, 0.0001);
	}
}
