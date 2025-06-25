package adapter.console;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

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
		ConsoleInvestmentRunner runner = new ConsoleInvestmentRunner(useCase, System.in, System.out);

		Assertions.assertNotNull(runner);
	}

	@Test
	void shouldPrintAmount() {
		String input = String.join(System.lineSeparator(),
			"예금",
			"1000000",
			"년",
			"1",
			"복리",
			"5",
			"비과세",
			"0"
		);
		InputStream inputStream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
		InvestmentFactory investmentFactory = new DefaultInvestmentFactory();
		InvestmentUseCase useCase = new CalculateInvestmentUseCase(investmentFactory);
		ConsoleInvestmentRunner runner = new ConsoleInvestmentRunner(useCase, inputStream, System.out);

		runner.run();
	}
}
