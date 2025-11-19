package co.invest72.investment.presentation.response;

import java.util.List;

import co.invest72.investment.application.dto.MonthlyCompoundInterestDetail;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CalculateMonthlyCompoundInterestResponse {
	private final Integer totalInvestment;
	private final Integer totalInterest;
	private final Integer totalProfit;
	private final List<MonthlyCompoundInterestDetail> details;

	@Builder
	private CalculateMonthlyCompoundInterestResponse(Integer totalInvestment, Integer totalInterest,
		Integer totalProfit, List<MonthlyCompoundInterestDetail> details) {
		this.totalInvestment = totalInvestment;
		this.totalInterest = totalInterest;
		this.totalProfit = totalProfit;
		this.details = details;
	}
}
