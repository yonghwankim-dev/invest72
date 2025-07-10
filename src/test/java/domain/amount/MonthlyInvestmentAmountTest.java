package domain.amount;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MonthlyInvestmentAmountTest {

	@ParameterizedTest
	@ValueSource(ints = {0, -1})
	void created_shouldThrowException_whenAmountIsInvalid(int amount) {
		assertThrows(IllegalArgumentException.class, () -> new MonthlyInvestmentAmount(amount));

	}
}
