import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MonthlyInstallmentInvestmentAmountTest {

	@Test
	void created(){
		InvestmentAmount investmentAmount = new MonthlyInstallmentInvestmentAmount(1_000_000);
		assertNotNull(investmentAmount);
	}

	@Test
	void shouldReturnAnnualInterest(){
		InvestmentAmount investmentAmount = new MonthlyInstallmentInvestmentAmount(1_000_000);
		InterestRate interestRate = new AnnualInterestRate(0.05);

		double annualInterest = investmentAmount.calInterest(interestRate);

		double expectedAnnualInterest = 50_000;
		assertEquals(expectedAnnualInterest, annualInterest, 0.001);
	}
}
