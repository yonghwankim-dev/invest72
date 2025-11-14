package co.invest72.investment.application;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CalculateGoalInvestmentTest {

	private CalculateGoalInvestment investment;

	@BeforeEach
	void setUp() {
		investment = new CalculateGoalInvestment();
	}

	@Test
	void canCreated() {
		Assertions.assertThat(investment).isNotNull();
	}

	@Test
	void calculate() {
		investment.calculate();
	}
}
