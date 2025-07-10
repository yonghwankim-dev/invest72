package domain.invest_amount;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MonthlyInvestmentAmountTest {

	private TargetAmountReachable monthlyInvestment;

	@BeforeEach
	void setUp() {
		monthlyInvestment = new MonthlyInvestmentAmount(1_000_000);
	}

	@ParameterizedTest
	@ValueSource(ints = {0, -1})
	void created_shouldThrowException_whenAmountIsInvalid(int amount) {
		assertThrows(IllegalArgumentException.class, () -> new MonthlyInvestmentAmount(amount));

	}

	@Test
	void calMonthsToReachTarget_shouldReturnMonthsToReachTarget() {
		int targetAmount = 10_000_000;

		int months = monthlyInvestment.calMonthsToReach(targetAmount);

		int expectedMonths = 9;
		assertEquals(expectedMonths, months);
	}
}
