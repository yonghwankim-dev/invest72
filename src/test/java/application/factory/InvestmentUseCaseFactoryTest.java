package application.factory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.usecase.CalculateInvestmentUseCase;
import application.usecase.InvestmentUseCase;
import domain.investment.ExpirationInvestment;
import domain.investment.MonthlyInvestment;

class InvestmentUseCaseFactoryTest {

	private InvestmentUseCaseFactory factory;

	@BeforeEach
	void setUp() {
		InvestmentFactory<ExpirationInvestment> investmentFactory = new DefaultInvestmentFactory();
		InvestmentFactory<MonthlyInvestment> monthlyInvestmentFactory = new MonthlyInvestmentFactory();
		factory = new InvestmentUseCaseFactory(investmentFactory, monthlyInvestmentFactory);
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
