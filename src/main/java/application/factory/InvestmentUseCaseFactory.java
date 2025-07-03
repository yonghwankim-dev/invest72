package application.factory;

import application.usecase.CalculateInvestmentUseCase;
import application.usecase.InvestmentUseCase;

public class InvestmentUseCaseFactory implements UseCaseFactory {

	private final InvestmentFactory investmentFactory;

	public InvestmentUseCaseFactory(InvestmentFactory investmentFactory) {
		this.investmentFactory = investmentFactory;
	}

	@Override
	public InvestmentUseCase createCalculateInvestmentUseCase() {
		return new CalculateInvestmentUseCase(investmentFactory);
	}
}
