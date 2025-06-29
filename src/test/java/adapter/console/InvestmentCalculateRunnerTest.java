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

import adapter.console.reader.AnnualInterestRateReader;
import adapter.console.reader.ConsoleInterestTypeReader;
import adapter.console.reader.DefaultInvestmentReaderDelegator;
import adapter.console.reader.FixedTaxRateReader;
import adapter.console.reader.InterestRatePercentReader;
import adapter.console.reader.InterestTypeReader;
import adapter.console.reader.InvestmentAmountReaderDelegator;
import adapter.console.reader.InvestmentReaderDelegator;
import adapter.console.reader.InvestmentTypeInputReader;
import adapter.console.reader.InvestmentTypeReader;
import adapter.console.reader.PeriodInputReader;
import adapter.console.reader.PeriodReader;
import adapter.console.reader.PeriodTypeInputReader;
import adapter.console.reader.PeriodTypeReader;
import adapter.console.reader.RegistryBasedInvestmentAmountDelegator;
import adapter.console.reader.TaxRateReader;
import adapter.console.reader.TaxTypeInputReader;
import adapter.console.reader.TaxTypeReader;
import adapter.console.writer.GuidePrinter;
import adapter.console.writer.WriterBasedGuidePrinter;
import application.CalculateInvestmentUseCase;
import application.DefaultInvestmentAmountReaderRegistry;
import application.DefaultInvestmentFactory;
import application.InvestmentAmountReaderRegistry;
import application.InvestmentFactory;
import application.InvestmentUseCase;
import application.KoreanStringBasedTaxableResolver;
import application.TaxableResolver;
import domain.tax.factory.KoreanTaxableFactory;
import domain.tax.factory.TaxableFactory;

class InvestmentCalculateRunnerTest {

	private InvestmentUseCase useCase;
	private ByteArrayOutputStream outputStream;
	private PrintStream printStream;
	private InputStream in;
	private InvestmentCalculateRunner runner;
	private InvestmentReaderDelegator investmentReaderDelegator;
	private TaxableResolver taxableResolver;
	private PrintStream err;

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
		in = System.in;
		outputStream = new ByteArrayOutputStream();
		printStream = new PrintStream(outputStream);
		err = System.err;
		InvestmentTypeReader investmentTypeReaderDelegator = new InvestmentTypeInputReader(
			guidePrinter);
		InvestmentAmountReaderDelegator investmentAmountReaderDelegator = new RegistryBasedInvestmentAmountDelegator(
			investmentAmountReaderRegistry);
		PeriodTypeReader periodTypeReader = new PeriodTypeInputReader(guidePrinter);
		PeriodReader periodReader = new PeriodInputReader(guidePrinter);
		InterestTypeReader interestTypeReader = new ConsoleInterestTypeReader(guidePrinter);
		InterestRatePercentReader interestRatePercentReader = new AnnualInterestRateReader(guidePrinter);
		TaxTypeReader taxTypeReader = new TaxTypeInputReader(guidePrinter);
		TaxRateReader taxRateReader = new FixedTaxRateReader(guidePrinter);
		investmentReaderDelegator = new DefaultInvestmentReaderDelegator(
			investmentTypeReaderDelegator,
			investmentAmountReaderDelegator,
			periodTypeReader,
			periodReader,
			interestTypeReader,
			interestRatePercentReader,
			taxTypeReader,
			taxRateReader
		);
		TaxableFactory taxableFactory = new KoreanTaxableFactory();
		taxableResolver = new KoreanStringBasedTaxableResolver(taxableFactory);
		runner = new InvestmentCalculateRunner(
			useCase,
			in,
			printStream,
			err,
			investmentReaderDelegator,
			taxableResolver
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
		in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
		runner = new InvestmentCalculateRunner(
			useCase,
			in,
			printStream,
			err,
			investmentReaderDelegator,
			taxableResolver
		);

		runner.run();

		String output = outputStream.toString(StandardCharsets.UTF_8);
		assertTrue(output.contains("total investment amount: 1051162원"));
	}
}
