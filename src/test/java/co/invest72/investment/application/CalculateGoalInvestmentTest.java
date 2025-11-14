package co.invest72.investment.application;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class CalculateGoalInvestmentTest {

	@Test
	void canCreated() {
		CalculateGoalInvestment investment = new CalculateGoalInvestment();

		Assertions.assertThat(investment).isNotNull();
	}
}
