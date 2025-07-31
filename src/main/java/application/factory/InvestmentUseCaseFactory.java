package application.factory;

import co.invest72.investment.application.CalculateInvestment;
import co.invest72.investment.application.InvestmentUseCase;
import co.invest72.investment.domain.Investment;

public class InvestmentUseCaseFactory implements UseCaseFactory {

	private final InvestmentFactory<Investment> investmentFactory;

	public InvestmentUseCaseFactory(InvestmentFactory<Investment> investmentFactory) {
		this.investmentFactory = investmentFactory;
	}

	@Override
	public InvestmentUseCase createCalculateInvestmentUseCase() {
		return new CalculateInvestment(investmentFactory);
	}
}
