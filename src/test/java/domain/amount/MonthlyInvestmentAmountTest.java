package domain.amount;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import domain.interest_rate.AnnualInterestRate;

class MonthlyInvestmentAmountTest {

	@ParameterizedTest
	@ValueSource(ints = {0, -1})
	void created_shouldThrowException_whenAmountIsInvalid(int amount) {
		assertThrows(IllegalArgumentException.class, () -> new MonthlyInvestmentAmount(amount));
	}

	@Test
	void calInterest_shouldReturnInterest() {
		TargetAmountReachable monthlyInvestmentAmount = new MonthlyInvestmentAmount(1_000_000);
		TargetAmount targetAmount = new DefaultTargetAmount(10_000_000);
		AnnualInterestRate interestRate = new AnnualInterestRate(0.05);

		int interest = monthlyInvestmentAmount.calInterest(targetAmount, interestRate);

		int expectedInterest = 41_660;
		assertEquals(expectedInterest, interest);
	}
}
