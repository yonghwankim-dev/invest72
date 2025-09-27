package co.invest72.investment.application;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import co.invest72.investment.application.dto.CalculateInvestmentRequest;
import co.invest72.investment.domain.Investment;

public class CalculateMonthlyInvestment {
	private final InvestmentFactory investmentFactory;

	public CalculateMonthlyInvestment(InvestmentFactory investmentFactory) {
		this.investmentFactory = investmentFactory;
	}

	public CalculateMonthlyInvestmentResponse calMonthlyInvestmentAmount(CalculateInvestmentRequest request) {
		List<MonthlyInvestmentResult> result = new ArrayList<>();
		Investment investment = investmentFactory.createBy(request);

		for (int month = 1; month <= investment.getFinalMonth(); month++) {
			result.add(new MonthlyInvestmentResult(
				month,
				investment.getPrincipal(month),
				investment.getInterest(month),
				investment.getTax(month),
				investment.getTotalProfit(month)
			));
		}
		return new CalculateMonthlyInvestmentResponse(result);
	}

	public record CalculateMonthlyInvestmentResponse(List<MonthlyInvestmentResult> monthlyInvestmentResults) {

		@Override
		public String toString() {
			String header = String.format("%-10s %-15s %-15s %-10s %-15s%n",
				"회차", "원금", "이자", "세금", "총수익금액");

			String body = monthlyInvestmentResults.stream()
				.map(result -> String.format("%-10d %-15d %-15d %-10d %-15d%n",
					result.month, result.principal, result.interest, result.tax, result.totalProfit))
				.collect(Collectors.joining());

			return header + body;
		}
	}

	public record MonthlyInvestmentResult(
		int month,
		int principal,
		int interest,
		int tax,
		int totalProfit) {
	}
}
