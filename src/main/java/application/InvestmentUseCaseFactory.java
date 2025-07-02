package application;

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
