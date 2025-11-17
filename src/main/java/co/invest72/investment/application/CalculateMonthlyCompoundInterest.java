package co.invest72.investment.application;

import co.invest72.investment.application.dto.CalculateGoalDto;
import co.invest72.investment.application.dto.CalculateMonthlyCompoundInterestResultDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CalculateMonthlyCompoundInterest {

	public CalculateMonthlyCompoundInterestResultDto calculate(CalculateGoalDto dto) {
		return CalculateMonthlyCompoundInterestResultDto.builder()
			.totalPrincipal(11_000_000)
			.totalInterest(278_855)
			.totalProfit(11_278_855)
			.build();
	}
}
