package application.factory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import co.invest72.investment.application.CalculateInvestment;
import co.invest72.investment.application.InvestmentUseCase;
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

		Assertions.assertInstanceOf(CalculateInvestment.class, useCase);
	}
}
