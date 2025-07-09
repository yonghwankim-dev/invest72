package application.factory;

import application.usecase.CalculateInvestmentUseCase;
import application.usecase.InvestmentUseCase;
import domain.investment.Investment;
import domain.investment.MonthlyInvestment;

public class InvestmentUseCaseFactory implements UseCaseFactory {

	private final InvestmentFactory<Investment> investmentFactory;
	private final InvestmentFactory<MonthlyInvestment> monthlyInvestmentFactory;

	public InvestmentUseCaseFactory(InvestmentFactory<Investment> investmentFactory,
		InvestmentFactory<MonthlyInvestment> monthlyInvestmentFactory) {
		this.investmentFactory = investmentFactory;
		this.monthlyInvestmentFactory = monthlyInvestmentFactory;
	}

	@Override
	public InvestmentUseCase createCalculateInvestmentUseCase() {
		return new CalculateInvestmentUseCase(investmentFactory, monthlyInvestmentFactory);
	}
}
