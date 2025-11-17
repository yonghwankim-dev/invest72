package co.invest72.investment.application;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import co.invest72.investment.application.dto.CalculateMonthlyCompoundInterestDto;
import co.invest72.investment.application.dto.CalculateMonthlyCompoundInterestResultDto;

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
			.annualInterestRate(0.05)
			.compoundingMethod("monthly")
			.build();

		CalculateMonthlyCompoundInterestResultDto actual = calculator.calculate(dto);

		CalculateMonthlyCompoundInterestResultDto expected = CalculateMonthlyCompoundInterestResultDto.builder()
			.totalPrincipal(11_000_000)
			.totalInterest(278_855)
			.totalProfit(11_278_855)
			.build();
		Assertions.assertThat(actual).isEqualTo(expected);
	}
}
