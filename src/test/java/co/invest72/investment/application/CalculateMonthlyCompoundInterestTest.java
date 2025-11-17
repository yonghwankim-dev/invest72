package co.invest72.investment.application;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class CalculateMonthlyCompoundInterestTest {

	@Test
	void canCreated() {
		CalculateMonthlyCompoundInterest calculator = new CalculateMonthlyCompoundInterest();
		
		Assertions.assertThat(calculator).isNotNull();
	}
}
