package application.usecase;

import application.factory.InvestmentFactory;
import application.request.CalculateInvestmentRequest;
import application.response.CalculateInvestmentResponse;
import domain.investment.Investment;

public class CalculateInvestmentUseCase implements InvestmentUseCase {

	private final InvestmentFactory investmentFactory;

	public CalculateInvestmentUseCase(InvestmentFactory investmentFactory) {
		this.investmentFactory = investmentFactory;
	}

	@Override
	public int calAmount(CalculateInvestmentRequest request) {
		Investment investment = investmentFactory.createBy(request);
		return investment.getAmount();
	}

	@Override
	public CalculateInvestmentResponse calInvestmentAmount(CalculateInvestmentRequest request) {
		Investment investment = investmentFactory.createBy(request);
		int totalProfitAmount = investment.getAmount();
		int totalPrincipalAmount = investment.getPrincipalAmount();
		int interest = investment.getInterest();
		int tax = 0;
		return new CalculateInvestmentResponse(totalProfitAmount, totalPrincipalAmount, interest, tax);
	}
}
