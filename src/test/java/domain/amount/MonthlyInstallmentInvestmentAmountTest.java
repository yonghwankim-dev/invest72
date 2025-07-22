package domain.amount;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import domain.interest_rate.AnnualInterestRate;
import domain.interest_rate.InterestRate;
import testutil.BigDecimalAssertion;

class MonthlyInstallmentInvestmentAmountTest {

	@Test
	void created() {
		InvestmentAmount investmentAmount = new MonthlyInstallmentInvestmentAmount(1_000_000);
		assertNotNull(investmentAmount);
	}

	@Test
	void shouldReturnAnnualInterest() {
		InvestmentAmount investmentAmount = new MonthlyInstallmentInvestmentAmount(1_000_000);
		InterestRate interestRate = new AnnualInterestRate(0.05);

		double annualInterest = investmentAmount.calAnnualInterest(interestRate);

		double expectedAnnualInterest = 50_000;
		assertEquals(expectedAnnualInterest, annualInterest, 0.001);
	}

	@Test
	void calMonthlyInterest_shouldReturnMonthlyInterest() {
		InvestmentAmount investmentAmount = new MonthlyInstallmentInvestmentAmount(1_000_000);
		InterestRate interestRate = new AnnualInterestRate(0.05);

		BigDecimal monthlyInterest = investmentAmount.calMonthlyInterest(interestRate);

		BigDecimal expectedMonthlyInterest = BigDecimal.valueOf(4166.666667);
		BigDecimalAssertion.assertBigDecimalEquals(expectedMonthlyInterest, monthlyInterest);
	}
}
