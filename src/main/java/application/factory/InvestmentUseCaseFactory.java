package application.factory;

import application.usecase.CalculateInvestmentUseCase;
import application.usecase.InvestmentUseCase;
import domain.investment.ExpirationInvestment;
import domain.investment.MonthlyInvestment;

public class InvestmentUseCaseFactory implements UseCaseFactory {

	private final InvestmentFactory<ExpirationInvestment> investmentFactory;
	private final InvestmentFactory<MonthlyInvestment> monthlyInvestmentFactory;

	public InvestmentUseCaseFactory(InvestmentFactory<ExpirationInvestment> investmentFactory,
		InvestmentFactory<MonthlyInvestment> monthlyInvestmentFactory) {
		this.investmentFactory = investmentFactory;
		this.monthlyInvestmentFactory = monthlyInvestmentFactory;
	}

	@Override
	public InvestmentUseCase createCalculateInvestmentUseCase() {
		return new CalculateInvestmentUseCase(investmentFactory, monthlyInvestmentFactory);
	}
}
