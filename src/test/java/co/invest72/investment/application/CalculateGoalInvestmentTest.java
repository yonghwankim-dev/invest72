package co.invest72.investment.application;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import co.invest72.investment.application.dto.CalculateGoalDto;
import co.invest72.investment.application.dto.CalculateGoalResultDto;

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
		CalculateGoalDto dto = CalculateGoalDto.builder()
			.monthlyInvestmentAmount(1_000_000)
			.annualInterestRate(0.05)
			.goalAmount(10_000_000)
			.startDate(LocalDate.of(2025, 1, 1))
			.build();

		CalculateGoalResultDto actual = investment.calculate(dto);

		CalculateGoalResultDto expected = new CalculateGoalResultDto(9);
		Assertions.assertThat(actual).isEqualTo(expected);
	}
}
