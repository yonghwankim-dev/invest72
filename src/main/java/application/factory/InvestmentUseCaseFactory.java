package application.factory;

import application.usecase.CalculateInvestmentUseCase;
import application.usecase.InvestmentUseCase;
import co.invest72.investment.domain.Investment;

public class InvestmentUseCaseFactory implements UseCaseFactory {

	private final InvestmentFactory<Investment> investmentFactory;

	public InvestmentUseCaseFactory(InvestmentFactory<Investment> investmentFactory) {
		this.investmentFactory = investmentFactory;
	}

	@Override
	public InvestmentUseCase createCalculateInvestmentUseCase() {
		return new CalculateInvestmentUseCase(investmentFactory);
	}
}
