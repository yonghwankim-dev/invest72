import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class YearlyInvestmentAmountTest {

	@Test
	void created(){
		InvestmentAmount investmentAmount = new YearlyInvestmentAmount(12_000_000);
		assertNotNull(investmentAmount);
	}

	@Test
	void shouldReturnAmount(){
		InvestmentAmount investmentAmount = new YearlyInvestmentAmount(12_000_000);

		int amount = investmentAmount.getMonthlyAmount();

		int expectedAmount = 1_000_000;
		assertEquals(expectedAmount, amount);
	}

	@Test
	void shouldReturnAnnualInterest() {
		InvestmentAmount investmentAmount = new YearlyInvestmentAmount(12_000_000);
		InterestRate interestRate = new AnnualInterestRate(0.05);

		double annualInterest = investmentAmount.getAnnualInterest(interestRate);

		double expectedAnnualInterest = 600_000; // 12,000,000 * 0.05
		assertEquals(expectedAnnualInterest, annualInterest, 0.01);
	}
}
