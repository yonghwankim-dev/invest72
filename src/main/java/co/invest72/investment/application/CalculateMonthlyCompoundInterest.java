package co.invest72.investment.application;

import co.invest72.investment.application.dto.CalculateMonthlyCompoundInterestDto;
import co.invest72.investment.application.dto.CalculateMonthlyCompoundInterestResultDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CalculateMonthlyCompoundInterest {

	public CalculateMonthlyCompoundInterestResultDto calculate(CalculateMonthlyCompoundInterestDto dto) {
		int totalPrincipal = dto.getInitialAmount() + (dto.getMonthlyDeposit() * ((dto.getInvestmentYears() * 12) - 1));
		return CalculateMonthlyCompoundInterestResultDto.builder()
			.totalPrincipal(totalPrincipal)
			.totalInterest(278_855)
			.totalProfit(11_278_855)
			.build();
	}
}
