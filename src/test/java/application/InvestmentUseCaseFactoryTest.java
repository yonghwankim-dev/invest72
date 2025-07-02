package application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InvestmentUseCaseFactoryTest {

	private InvestmentUseCaseFactory factory;

	@BeforeEach
	void setUp() {
		InvestmentFactory investmentFactory = new DefaultInvestmentFactory();
		factory = new InvestmentUseCaseFactory(investmentFactory);
	}

	@Test
	void created() {
		Assertions.assertNotNull(factory);
	}

	@Test
	void createCalculateInvestmentUseCase() {
		InvestmentUseCase useCase = factory.createCalculateInvestmentUseCase();

		Assertions.assertInstanceOf(CalculateInvestmentUseCase.class, useCase);
	}
}
