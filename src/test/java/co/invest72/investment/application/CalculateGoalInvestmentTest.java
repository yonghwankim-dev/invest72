package co.invest72.investment.application;

import java.time.LocalDate;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import co.invest72.investment.application.dto.CalculateGoalDto;
import co.invest72.investment.application.dto.CalculateGoalResultDto;
import co.invest72.investment.application.dto.GoalDetailResultDto;

class CalculateGoalInvestmentTest {

	private CalculateGoalInvestment investment;
	private CalculateGoalDto dto;

	@BeforeEach
	void setUp() {
		investment = new CalculateGoalInvestment();
		LocalDate startDate = LocalDate.of(2025, 1, 1);
		dto = CalculateGoalDto.builder()
			.monthlyInvestmentAmount(1_000_000)
			.annualInterestRate(0.05)
			.goalAmount(10_000_000)
			.startDate(startDate)
			.build();
	}

	@Test
	void canCreated() {
		Assertions.assertThat(investment).isNotNull();
	}

	@Test
	void calculate() {
		CalculateGoalResultDto actual = investment.calculate(dto);

		int months = 9;
		LocalDate achievedDate = LocalDate.of(2025, 10, 1);
		CalculateGoalResultDto expected = new CalculateGoalResultDto(months, achievedDate);
		Assertions.assertThat(actual).isEqualTo(expected);
	}

	@Test
	void calculate_whenInvalidMonthlyInvestmentAmount_thenThrowException() {
		LocalDate startDate = LocalDate.of(2025, 1, 1);
		dto = CalculateGoalDto.builder()
			.monthlyInvestmentAmount(0)
			.annualInterestRate(0.05)
			.goalAmount(10_000_000)
			.startDate(startDate)
			.build();

		Throwable throwable = Assertions.catchThrowable(() -> investment.calculate(dto));

		Assertions.assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void calculateMonthlyDetails() {
		List<GoalDetailResultDto> result = investment.calculateMonthlyDetails(dto);

		Assertions.assertThat(result).isEmpty();
	}
}
