package application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class InvestmentUseCaseFactoryTest {

	@Test
	void created() {
		InvestmentFactory investmentFactory = new DefaultInvestmentFactory();
		InvestmentUseCaseFactory factory = new InvestmentUseCaseFactory(investmentFactory);
		Assertions.assertNotNull(factory);
	}

	@Test
	void createCalculateInvestmentUseCase() {
		InvestmentFactory investmentFactory = new DefaultInvestmentFactory();
		InvestmentUseCaseFactory factory = new InvestmentUseCaseFactory(investmentFactory);

		InvestmentUseCase useCase = factory.createCalculateInvestmentUseCase();

		Assertions.assertInstanceOf(CalculateInvestmentUseCase.class, useCase);
	}
}
