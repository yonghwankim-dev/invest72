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
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import adapter.console.reader.BufferedReaderBasedInvestReader;
import adapter.console.ui.WriterBasedGuidePrinter;
import adapter.ui.GuidePrinter;
import application.factory.DefaultInvestmentFactory;
import application.builder.DefaultInvestmentRequestBuilder;
import application.factory.InvestmentFactory;
import application.builder.InvestmentRequestBuilder;
import application.factory.InvestmentUseCaseFactory;
import application.resolver.KoreanStringBasedTaxableResolver;
import application.resolver.TaxableResolver;
import application.factory.UseCaseFactory;
import application.delegator.CalculateInvestmentReaderDelegator;
import application.delegator.InvestmentReaderDelegator;
import application.reader.InvestReader;
import application.registry.InvestmentAmountReaderStrategyRegistry;
import application.registry.MapBasedInvestmentAmountReaderStrategyRegistry;
import application.strategy.FixedDepositAmountReaderStrategy;
import application.strategy.InstallmentSavingAmountReaderStrategy;
import application.strategy.InvestmentAmountReaderStrategy;
import domain.tax.factory.KoreanTaxableFactory;
import domain.tax.factory.TaxableFactory;
import domain.type.InvestmentType;

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
	private InvestmentAmountReaderStrategyRegistry amountReaderStrategyRegistry;

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
		investReader = new BufferedReaderBasedInvestReader(reader, guidePrinter);
		Map<InvestmentType, InvestmentAmountReaderStrategy> amountReaderStrategies = Map.of(
			InvestmentType.FIXED_DEPOSIT, new FixedDepositAmountReaderStrategy(),
			InvestmentType.INSTALLMENT_SAVING, new InstallmentSavingAmountReaderStrategy()
		);
		amountReaderStrategyRegistry = new MapBasedInvestmentAmountReaderStrategyRegistry(amountReaderStrategies);
		investmentReaderDelegator = new CalculateInvestmentReaderDelegator(
			investReader, requestBuilder, amountReaderStrategyRegistry
		);
		TaxableFactory taxableFactory = new KoreanTaxableFactory();
		taxableResolver = new KoreanStringBasedTaxableResolver(taxableFactory);
		runner = new CalculateInvestmentRunner(
			printStream, err, useCaseFactory,
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
		in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
		reader = new BufferedReader(new InputStreamReader(in));
		investReader = new BufferedReaderBasedInvestReader(reader, guidePrinter);
		investmentReaderDelegator = new CalculateInvestmentReaderDelegator(
			investReader, requestBuilder, amountReaderStrategyRegistry
		);
		runner = new CalculateInvestmentRunner(
			printStream, err, useCaseFactory,
			investmentReaderDelegator
		);

		runner.run();

		String output = outputStream.toString(StandardCharsets.UTF_8);
		assertTrue(output.contains("total investment amount: 1051162원"));
	}
}
