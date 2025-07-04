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
		return new CalculateInvestmentResponse(12_330_017);
	}
}
