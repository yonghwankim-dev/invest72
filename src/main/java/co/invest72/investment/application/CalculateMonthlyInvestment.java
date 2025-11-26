package co.invest72.investment.application;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import co.invest72.investment.domain.Investment;
import co.invest72.investment.presentation.request.CalculateInvestmentRequest;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
public class CalculateMonthlyInvestment {
	private final InvestmentFactory investmentFactory;
	private final TaxFormatter taxFormatter;

	public CalculateMonthlyInvestmentResponse calMonthlyInvestmentAmount(CalculateInvestmentRequest request) {
		List<MonthlyInvestmentResult> result = new ArrayList<>();
		Investment investment = investmentFactory.createBy(request);

		for (int month = 1; month <= investment.getFinalMonth(); month++) {
			result.add(new MonthlyInvestmentResult(
				month,
				investment.getPrincipal(month),
				investment.getInterest(month),
				investment.getProfit(month)
			));
		}
		int totalInvestment = investment.getTotalInvestment();
		int totalPrincipal = investment.getTotalPrincipal();
		int totalInterest = investment.getTotalInterest();
		int totalTax = investment.getTotalTax();
		int totalProfit = investment.getTotalProfit();
		String taxType = investment.getTaxType();
		String taxPercent = taxFormatter.format(request.getTaxRate());
		return CalculateMonthlyInvestmentResponse.builder()
			.totalInvestment(totalInvestment)
			.totalPrincipal(totalPrincipal)
			.totalInterest(totalInterest)
			.totalTax(totalTax)
			.totalProfit(totalProfit)
			.taxType(taxType)
			.taxPercent(taxPercent)
			.details(result)
			.build();
	}

	@EqualsAndHashCode
	@Getter
	public static final class CalculateMonthlyInvestmentResponse {
		private final int totalInvestment;
		private final int totalPrincipal;
		private final int totalInterest;
		private final int totalTax;
		private final int totalProfit;
		private final String taxType;
		private final String taxPercent;
		private final List<MonthlyInvestmentResult> details;

		@Builder
		public CalculateMonthlyInvestmentResponse(int totalInvestment, int totalPrincipal, int totalInterest,
			int totalTax, int totalProfit, String taxType, String taxPercent, List<MonthlyInvestmentResult> details) {
			this.totalInvestment = totalInvestment;
			this.totalPrincipal = totalPrincipal;
			this.totalInterest = totalInterest;
			this.totalTax = totalTax;
			this.totalProfit = totalProfit;
			this.taxType = taxType;
			this.taxPercent = taxPercent;
			this.details = details;
		}

		@Override
		public String toString() {
			String header = String.format("%-10s %-15s %-15s %-15s%n",
				"회차", "원금", "이자", "수익");

			String body = details.stream()
				.map(result -> String.format("%-10d %-15d %-15d %-15d%n",
					result.month, result.principal, result.interest, result.profit))
				.collect(Collectors.joining());
			String footer = String.format("총 원금: %,d원, 총 이자: %,d원, 총 세금: %,d원, 총 수익 금액: %,d원%n",
				totalPrincipal, totalInterest, totalTax, totalProfit);
			return header + body + footer;
		}
	}

	@RequiredArgsConstructor
	@Getter
	@ToString
	@EqualsAndHashCode
	public static final class MonthlyInvestmentResult {
		private final int month;
		private final int principal;
		private final int interest;
		private final int profit;
	}
}
