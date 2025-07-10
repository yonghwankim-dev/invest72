package domain.invest_amount;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import domain.interest_rate.AnnualInterestRate;
import domain.interest_rate.InterestRate;

class MonthlyInvestmentAmountTest {

	@Test
	void created() {
		InvestmentAmount investmentAmount = new MonthlyInvestmentAmount(1_000_000);
		assertNotNull(investmentAmount);
	}

	@Test
	void calAnnualInterest_shouldReturnAnnualInterest() {
		InvestmentAmount investmentAmount = new MonthlyInvestmentAmount(1_000_000);
		InterestRate interestRate = new AnnualInterestRate(0.05);

		double annualInterest = investmentAmount.calAnnualInterest(interestRate);

		double expectedAnnualInterest = 50_000;
		assertEquals(expectedAnnualInterest, annualInterest, 0.01);
	}
}
