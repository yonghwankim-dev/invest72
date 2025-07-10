package domain.amount;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class MonthlyInvestmentAmountTest {

	private TargetAmountReachable monthlyInvestment;

	public static Stream<Arguments> calMonthsToReachTargetSource() {
		return Stream.of(
			Arguments.of(1_000_000, 10),
			Arguments.of(3_000_000, 4),
			Arguments.of(10_000_000, 1),
			Arguments.of(12_000_000, 1)
		);
	}

	@BeforeEach
	void setUp() {
		monthlyInvestment = new MonthlyInvestmentAmount(1_000_000);
	}

	@ParameterizedTest
	@ValueSource(ints = {0, -1})
	void created_shouldThrowException_whenAmountIsInvalid(int amount) {
		assertThrows(IllegalArgumentException.class, () -> new MonthlyInvestmentAmount(amount));

	}

	@ParameterizedTest
	@MethodSource(value = "calMonthsToReachTargetSource")
	void calMonthsToReachTarget_shouldReturnMonthsToReachTarget(int amount, int expectedMonths) {
		monthlyInvestment = new MonthlyInvestmentAmount(amount);
		int targetAmount = 10_000_000;
		int months = monthlyInvestment.calMonthsToReach(targetAmount);

		assertEquals(expectedMonths, months);
	}
}
