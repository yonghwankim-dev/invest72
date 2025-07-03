package application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.factory.DefaultInvestmentFactory;
import application.factory.InvestmentFactory;
import application.factory.InvestmentUseCaseFactory;
import application.usecase.CalculateInvestmentUseCase;
import application.usecase.InvestmentUseCase;

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
