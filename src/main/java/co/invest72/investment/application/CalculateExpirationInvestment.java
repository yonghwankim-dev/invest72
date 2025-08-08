package co.invest72.investment.application;

import co.invest72.investment.application.dto.CalculateInvestmentRequest;
import co.invest72.investment.domain.Investment;

public class CalculateExpirationInvestment {

	private final InvestmentFactory investmentFactory;

	public CalculateExpirationInvestment(InvestmentFactory investmentFactory) {
		this.investmentFactory = investmentFactory;
	}

	public CalculateExpirationInvestmentResponse calInvestment(CalculateInvestmentRequest request) {
		Investment investment = investmentFactory.createBy(request);
		int totalProfitAmount = investment.getTotalProfit();
		int totalPrincipalAmount = investment.getPrincipal();
		int interest = investment.getInterest();
		int tax = investment.getTax();
		return new CalculateExpirationInvestmentResponse(totalProfitAmount, totalPrincipalAmount, interest, tax);
	}

	public record CalculateExpirationInvestmentResponse(
		int totalProfitAmount,
		int totalPrincipalAmount,
		int interest,
		int tax) {
	}
}
