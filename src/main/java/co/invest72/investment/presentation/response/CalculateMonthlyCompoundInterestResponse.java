package co.invest72.investment.presentation.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CalculateMonthlyCompoundInterestResponse {
	private final Integer totalInvestment;
	private final Integer totalInterest;
	private final Integer totalProfit;

	@Builder
	private CalculateMonthlyCompoundInterestResponse(Integer totalInvestment, Integer totalInterest,
		Integer totalProfit) {
		this.totalInvestment = totalInvestment;
		this.totalInterest = totalInterest;
		this.totalProfit = totalProfit;
	}
}
