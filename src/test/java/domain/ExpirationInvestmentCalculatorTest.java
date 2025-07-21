package domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ExpirationInvestmentCalculatorTest {
	@Test
	void created() {
		InvestmentCalculator calculator = new ExpirationInvestmentCalculator();

		Assertions.assertNotNull(calculator);
	}
}
