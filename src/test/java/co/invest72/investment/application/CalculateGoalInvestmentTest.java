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
		LocalDate startDate = LocalDate.of(2025, 1, 1);
		CalculateGoalDto dto = CalculateGoalDto.builder()
			.monthlyInvestmentAmount(1_000_000)
			.annualInterestRate(0.05)
			.goalAmount(10_000_000)
			.startDate(startDate)
			.build();

		CalculateGoalResultDto actual = investment.calculate(dto);

		int months = 9;
		LocalDate achievedDate = LocalDate.of(2025, 10, 1);
		CalculateGoalResultDto expected = new CalculateGoalResultDto(months, achievedDate);
		Assertions.assertThat(actual).isEqualTo(expected);
	}

	@Test
	void calculate_whenInvalidMonthlyInvestmentAmount_thenThrowException() {
		LocalDate startDate = LocalDate.of(2025, 1, 1);
		CalculateGoalDto dto = CalculateGoalDto.builder()
			.monthlyInvestmentAmount(0)
			.annualInterestRate(0.05)
			.goalAmount(10_000_000)
			.startDate(startDate)
			.build();

		Throwable throwable = Assertions.catchThrowable(() -> investment.calculate(dto));

		Assertions.assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
	}
}
