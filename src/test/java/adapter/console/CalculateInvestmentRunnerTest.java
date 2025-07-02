package adapter.console;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import adapter.console.reader.BufferedReaderBasedInvestReader;
import adapter.console.reader.CalculateInvestmentReaderDelegator;
import adapter.console.reader.InvestReader;
import adapter.console.reader.InvestmentReaderDelegator;
import adapter.console.writer.GuidePrinter;
import adapter.console.writer.WriterBasedGuidePrinter;
import application.DefaultInvestmentFactory;
import application.DefaultInvestmentRequestBuilder;
import application.InvestmentFactory;
import application.InvestmentRequestBuilder;
import application.InvestmentUseCaseFactory;
import application.KoreanStringBasedTaxableResolver;
import application.TaxableResolver;
import application.UseCaseFactory;
import domain.tax.factory.KoreanTaxableFactory;
import domain.tax.factory.TaxableFactory;

class CalculateInvestmentRunnerTest {

	private UseCaseFactory useCaseFactory;
	private ByteArrayOutputStream outputStream;
	private PrintStream printStream;
	private GuidePrinter guidePrinter;
	private InputStream in;
	private CalculateInvestmentRunner runner;
	private InvestmentReaderDelegator investmentReaderDelegator;
	private TaxableResolver taxableResolver;
	private PrintStream err;
	private InvestmentRequestBuilder requestBuilder;
	private BufferedReader reader;
	private InvestReader investReader;

	@BeforeEach
	void setUp() {
		InvestmentFactory investmentFactory = new DefaultInvestmentFactory();
		useCaseFactory = new InvestmentUseCaseFactory(investmentFactory);
		PrintStream out = System.out;
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(out);
		BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
		guidePrinter = new WriterBasedGuidePrinter(bufferedWriter);
		in = System.in;
		outputStream = new ByteArrayOutputStream();
		printStream = new PrintStream(outputStream);
		err = System.err;
		requestBuilder = new DefaultInvestmentRequestBuilder();
		reader = new BufferedReader(new InputStreamReader(in));
		investReader = new BufferedReaderBasedInvestReader(guidePrinter, reader);
		investmentReaderDelegator = new CalculateInvestmentReaderDelegator(
			requestBuilder,
			investReader
		);
		TaxableFactory taxableFactory = new KoreanTaxableFactory();
		taxableResolver = new KoreanStringBasedTaxableResolver(taxableFactory);
		runner = new CalculateInvestmentRunner(
			useCaseFactory,
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
		reader = new BufferedReader(new InputStreamReader(in));
		investReader = new BufferedReaderBasedInvestReader(guidePrinter, reader);
		investmentReaderDelegator = new CalculateInvestmentReaderDelegator(
			requestBuilder,
			investReader
		);
		runner = new CalculateInvestmentRunner(
			useCaseFactory,
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
