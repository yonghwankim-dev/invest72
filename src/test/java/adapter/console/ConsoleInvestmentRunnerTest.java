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

import adapter.console.reader.ConsoleInterestRatePercentReader;
import adapter.console.reader.ConsoleInterestTypeReader;
import adapter.console.reader.ConsoleInvestmentAmountReaderDelegator;
import adapter.console.reader.ConsoleInvestmentReaderDelegator;
import adapter.console.reader.ConsoleInvestmentTypeReaderDelegator;
import adapter.console.reader.ConsolePeriodReaderDelegator;
import adapter.console.reader.ConsolePeriodTypeReaderDelegator;
import adapter.console.reader.InterestRatePercentReader;
import adapter.console.reader.InterestTypeReader;
import adapter.console.reader.InvestmentAmountReaderDelegator;
import adapter.console.reader.InvestmentReaderDelegator;
import adapter.console.reader.InvestmentTypeReaderDelegator;
import adapter.console.reader.PeriodReaderDelegator;
import adapter.console.reader.PeriodTypeReaderDelegator;
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
	private InputStream inputStream;
	private ConsoleInvestmentRunner runner;
	private InvestmentReaderDelegator investmentReaderDelegator;

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
		InvestmentTypeReaderDelegator investmentTypeReaderDelegator = new ConsoleInvestmentTypeReaderDelegator(
			guidePrinter);
		InvestmentAmountReaderDelegator investmentAmountReaderDelegator = new ConsoleInvestmentAmountReaderDelegator(
			investmentAmountReaderRegistry);
		PeriodTypeReaderDelegator periodTypeReaderDelegator = new ConsolePeriodTypeReaderDelegator(guidePrinter);
		PeriodReaderDelegator periodReaderDelegator = new ConsolePeriodReaderDelegator(guidePrinter);
		InterestTypeReader interestTypeReader = new ConsoleInterestTypeReader(guidePrinter);
		InterestRatePercentReader interestRatePercentReader = new ConsoleInterestRatePercentReader(guidePrinter);
		investmentReaderDelegator = new ConsoleInvestmentReaderDelegator(
			investmentTypeReaderDelegator,
			investmentAmountReaderDelegator,
			periodTypeReaderDelegator,
			periodReaderDelegator,
			interestTypeReader,
			interestRatePercentReader
		);

		runner = new ConsoleInvestmentRunner(
			useCase,
			inputStream,
			printStream,
			investmentReaderDelegator
		);
	}

	@Test
	void created() {
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
		runner = new ConsoleInvestmentRunner(
			useCase,
			inputStream,
			printStream,
			investmentReaderDelegator
		);

		runner.run();

		String output = outputStream.toString(StandardCharsets.UTF_8);
		assertTrue(output.contains("total investment amount: 1051162원"));
	}
}
