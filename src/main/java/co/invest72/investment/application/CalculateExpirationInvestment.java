package co.invest72.investment.application;

import co.invest72.investment.domain.Investment;
import co.invest72.investment.presentation.request.CalculateInvestmentRequest;

public class CalculateExpirationInvestment {

	private final InvestmentFactory investmentFactory;

	public CalculateExpirationInvestment(InvestmentFactory investmentFactory) {
		this.investmentFactory = investmentFactory;
	}

	public CalculateExpirationInvestmentResponse calInvestment(CalculateInvestmentRequest request) {
		Investment investment = investmentFactory.createBy(request);
		int totalInvestment = investment.getTotalInvestment();
		int totalProfit = investment.getTotalProfit();
		int totalPrincipal = investment.getTotalPrincipal();
		int interest = investment.getTotalInterest();
		int tax = investment.getTotalTax();
		String taxType = investment.getTaxType();
		return new CalculateExpirationInvestmentResponse(totalInvestment, totalPrincipal, interest, tax, totalProfit,
			taxType);
	}

	public record CalculateExpirationInvestmentResponse(
		int totalInvestment, int totalPrincipal, int totalInterest, int totalTax, int totalProfit, String taxType) {
	}
}
