package domain.invest_amount;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.interest_rate.AnnualInterestRate;
import domain.interest_rate.InterestRate;

class MonthlyInvestmentAmountTest {

	private InvestmentAmount investmentAmount;
	private InterestRate interestRate;

	@BeforeEach
	void setUp() {
		investmentAmount = new MonthlyInvestmentAmount(1_000_000);
		interestRate = new AnnualInterestRate(0.05);
	}

	@Test
	void created() {
		assertNotNull(investmentAmount);
	}

	@Test
	void calAnnualInterest_shouldReturnAnnualInterest() {
		double annualInterest = investmentAmount.calAnnualInterest(interestRate);

		double expectedAnnualInterest = 50_000;
		assertEquals(expectedAnnualInterest, annualInterest, 0.01);
	}
}
