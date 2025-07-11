package domain.amount;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class DefaultTargetAmountTest {

	@ParameterizedTest
	@ValueSource(ints = {0, -1, -1000})
	void created_shouldThrowException_whenAmountIsInvalid(int amount) {
		assertThrows(IllegalArgumentException.class, () -> new DefaultTargetAmount(amount));
	}

}
