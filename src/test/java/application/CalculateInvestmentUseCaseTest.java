package application;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CalculateInvestmentUseCaseTest {

	@Test
	void created() {
		InvestmentFactory investmentFactory = new DefaultInvestmentFactory();

		InvestmentUseCase investmentUseCase = new CalculateInvestmentUseCase(investmentFactory);

		assertNotNull(investmentUseCase);
	}
}
