package co.invest72.investment.presentation.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CalculateMonthlyCompoundInterestResponse {
	private final Integer totalPrincipal;
	private final Integer totalInterest;
	private final Integer totalProfit;

	@Builder
	private CalculateMonthlyCompoundInterestResponse(Integer totalPrincipal, Integer totalInterest,
		Integer totalProfit) {
		this.totalPrincipal = totalPrincipal;
		this.totalInterest = totalInterest;
		this.totalProfit = totalProfit;
	}
}
