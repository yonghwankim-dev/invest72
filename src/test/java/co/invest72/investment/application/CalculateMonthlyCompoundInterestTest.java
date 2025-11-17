package co.invest72.investment.application;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import co.invest72.investment.application.dto.CalculateMonthlyCompoundInterestDto;

class CalculateMonthlyCompoundInterestTest {

	@Test
	void canCreated() {
		CalculateMonthlyCompoundInterest calculator = new CalculateMonthlyCompoundInterest();

		Assertions.assertThat(calculator).isNotNull();
	}

	@Test
	void calculate() {
		CalculateMonthlyCompoundInterest calculator = new CalculateMonthlyCompoundInterest();
		CalculateMonthlyCompoundInterestDto dto = CalculateMonthlyCompoundInterestDto.builder()
			.initialAmount(0)
			.monthlyDeposit(1_000_000)
			.investmentYears(1)
			.annualInterestRate(5.0)
			.compoundingMethod("monthly")
			.build();
		calculator.calculate(dto);
	}
}
