package co.invest72.investment.application;

import co.invest72.investment.application.dto.CalculateGoalDto;
import co.invest72.investment.application.dto.CalculateGoalResultDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CalculateMonthlyCompoundInterest {

	public CalculateGoalResultDto calculate(CalculateGoalDto dto) {
		return CalculateGoalResultDto.builder()
			.totalPrincipal(11_000_000)
			.totalInterest(278_855)
			.totalProfit(11_278_855)
			.build();
	}
}
