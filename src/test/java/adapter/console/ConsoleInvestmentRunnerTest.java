package adapter.console;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import adapter.console.reader.ConsoleInvestmentAmountReaderDelegator;
import adapter.console.reader.InvestmentAmountReaderDelegator;
import adapter.console.writer.GuidePrinter;
import adapter.console.writer.WriterBasedGuidePrinter;
import application.CalculateInvestmentUseCase;
import application.DefaultInvestmentAmountReaderRegistry;
import application.DefaultInvestmentFactory;
import application.InvestmentAmountReaderRegistry;
import application.InvestmentFactory;
import application.InvestmentUseCase;

class ConsoleInvestmentRunnerTest {

	private InvestmentUseCase useCase;
	private ByteArrayOutputStream outputStream;
	private PrintStream printStream;
	private InvestmentAmountReaderDelegator investmentAmountReaderDelegator;
	private InputStream inputStream;

	@BeforeEach
	void setUp() {
		InvestmentFactory investmentFactory = new DefaultInvestmentFactory();
		useCase = new CalculateInvestmentUseCase(investmentFactory);
		PrintStream out = System.out;
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(out);
		BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
		GuidePrinter guidePrinter = new WriterBasedGuidePrinter(bufferedWriter);
		InvestmentAmountReaderRegistry investmentAmountReaderRegistry = new DefaultInvestmentAmountReaderRegistry(
			guidePrinter);
		inputStream = System.in;
		outputStream = new ByteArrayOutputStream();
		printStream = new PrintStream(outputStream);
		investmentAmountReaderDelegator = new ConsoleInvestmentAmountReaderDelegator(investmentAmountReaderRegistry);
	}

	@Test
	void created() {
		ConsoleInvestmentRunner runner = new ConsoleInvestmentRunner(useCase, inputStream, printStream,
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
		inputStream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
		ConsoleInvestmentRunner runner = new ConsoleInvestmentRunner(useCase, inputStream, printStream,
			investmentAmountReaderDelegator);

		runner.run();

		String output = outputStream.toString(StandardCharsets.UTF_8);
		assertTrue(output.contains("total investment amount: 1051162원"));
	}
}
