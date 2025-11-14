package co.invest72.investment.application;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import co.invest72.investment.application.dto.CalculateGoalDto;

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
		CalculateGoalDto dto = CalculateGoalDto.builder().build();

		int months = investment.calculate(dto);

		Assertions.assertThat(months).isEqualTo(0);
	}
}
