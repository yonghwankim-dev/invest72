package domain.invest_amount;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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

	@ParameterizedTest
	@ValueSource(ints = {0, -1})
	void created_shouldThrowException_whenAmountIsInvalid(int amount) {
		assertThrows(IllegalArgumentException.class, () -> new MonthlyInvestmentAmount(amount));

	}

	@Test
	void calAnnualInterest_shouldReturnAnnualInterest() {
		double annualInterest = investmentAmount.calAnnualInterest(interestRate);

		double expectedAnnualInterest = 50_000;
		assertEquals(expectedAnnualInterest, annualInterest, 0.01);
	}

	@Test
	void calMonthlyInterest_shouldReturnMonthlyInterest() {
		double monthlyInterest = investmentAmount.calMonthlyInterest(interestRate);

		double expectedAnnualInterest = 4166;
		assertEquals(expectedAnnualInterest, monthlyInterest, 0.01);
	}
}
