package adapter.console;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.junit.jupiter.api.Test;

import adapter.console.reader.ConsoleInvestmentAmountReaderDelegator;
import adapter.console.reader.FixedDepositAmountReader;
import adapter.console.reader.InstallmentInvestmentAmountReader;
import adapter.console.reader.InvestmentAmountReader;
import adapter.console.reader.InvestmentAmountReaderDelegator;
import application.CalculateInvestmentUseCase;
import application.DefaultInvestmentFactory;
import application.InvestmentFactory;
import application.InvestmentUseCase;

class ConsoleInvestmentRunnerTest {

	@Test
	void created() {
		InvestmentFactory investmentFactory = new DefaultInvestmentFactory();
		InvestmentUseCase useCase = new CalculateInvestmentUseCase(investmentFactory);
		PrintStream out = System.out;
		List<InvestmentAmountReader> investmentAmountReaders = List.of(
			new FixedDepositAmountReader(out),
			new InstallmentInvestmentAmountReader(out)
		);
		InvestmentAmountReaderDelegator investmentAmountReaderDelegator = new ConsoleInvestmentAmountReaderDelegator(
			investmentAmountReaders);
		ConsoleInvestmentRunner runner = new ConsoleInvestmentRunner(useCase, System.in, out,
			investmentAmountReaderDelegator);

		assertNotNull(runner);
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

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		PrintStream printStream = new PrintStream(outputStream);

		InvestmentFactory investmentFactory = new DefaultInvestmentFactory();
		InvestmentUseCase useCase = new CalculateInvestmentUseCase(investmentFactory);
		List<InvestmentAmountReader> investmentAmountReaders = List.of(
			new FixedDepositAmountReader(printStream),
			new InstallmentInvestmentAmountReader(printStream)
		);
		InvestmentAmountReaderDelegator investmentAmountReaderDelegator = new ConsoleInvestmentAmountReaderDelegator(
			investmentAmountReaders);
		ConsoleInvestmentRunner runner = new ConsoleInvestmentRunner(useCase, inputStream, printStream,
			investmentAmountReaderDelegator);

		runner.run();

		String output = outputStream.toString(StandardCharsets.UTF_8);
		assertTrue(output.contains("total investment amount: 1051162원"));
	}
}
