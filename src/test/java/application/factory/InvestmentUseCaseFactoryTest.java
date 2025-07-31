package application.factory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.usecase.CalculateInvestmentUseCase;
import application.usecase.InvestmentUseCase;
import co.invest72.investment.domain.Investment;

class InvestmentUseCaseFactoryTest {

	private InvestmentUseCaseFactory factory;

	@BeforeEach
	void setUp() {
		InvestmentFactory<Investment> investmentFactory = new ExpirationInvestmentFactory();
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
