package adapter.console;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import application.CalculateInvestmentUseCase;
import application.DefaultInvestmentFactory;
import application.InvestmentFactory;
import application.InvestmentUseCase;

class ConsoleInvestmentRunnerTest {

	@Test
	void created() {
		InvestmentFactory investmentFactory = new DefaultInvestmentFactory();
		InvestmentUseCase useCase = new CalculateInvestmentUseCase(investmentFactory);
		ConsoleInvestmentRunner runner = new ConsoleInvestmentRunner(useCase, System.in);

		Assertions.assertNotNull(runner);
	}

	// todo: implement test for ConsoleInvestmentRunner.run() method
}
