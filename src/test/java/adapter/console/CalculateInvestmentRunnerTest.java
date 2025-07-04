package adapter.console;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import adapter.InvestmentApplicationRunner;
import adapter.console.ui.BufferedWriterBasedGuidePrinter;
import adapter.ui.GuidePrinter;
import application.builder.DefaultInvestmentRequestBuilder;
import application.builder.InvestmentRequestBuilder;
import application.delegator.CalculateInvestmentReaderDelegator;
import application.delegator.InvestmentReaderDelegator;
import application.factory.DefaultInvestmentFactory;
import application.factory.InvestmentFactory;
import application.factory.InvestmentUseCaseFactory;
import application.factory.UseCaseFactory;
import application.reader.InvestReader;
import application.reader.impl.BufferedReaderBasedInvestReader;
import application.registry.InvestmentAmountReaderStrategyRegistry;
import application.registry.MapBasedInvestmentAmountReaderStrategyRegistry;
import application.strategy.FixedDepositAmountReaderStrategy;
import application.strategy.InstallmentSavingAmountReaderStrategy;
import application.strategy.InvestmentAmountReaderStrategy;
import domain.type.InvestmentType;

class CalculateInvestmentRunnerTest {

	private UseCaseFactory useCaseFactory;
	private ByteArrayOutputStream outputStream;
	private PrintStream printStream;
	private GuidePrinter guidePrinter;
	private InputStream in;
	private InvestmentApplicationRunner runner;
	private InvestmentReaderDelegator investmentReaderDelegator;
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
		in = System.in;
		outputStream = new ByteArrayOutputStream();
		printStream = new PrintStream(outputStream);
		err = System.err;
		guidePrinter = new BufferedWriterBasedGuidePrinter(bufferedWriter, err);
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
	void shouldPrintAmount() throws FileNotFoundException {
		File file = new File("src/test/resources/test_input1.txt");
		in = new FileInputStream(file);
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
		String expected = getExpectedFileContent("src/test/resources/expected_output1.txt");
		assertEquals(expected, output);
	}

	@Test
	void shouldPrintAmount_whenInvestmentTypeIsInstallmentSaving() throws FileNotFoundException {
		File file = new File("src/test/resources/test_input2.txt");
		in = new FileInputStream(file);
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
		String expected = getExpectedFileContent("src/test/resources/expected_output2.txt");
		assertEquals(expected, output);
	}

	private String getExpectedFileContent(String path) {
		try (BufferedReader expectedReader = new BufferedReader(
			new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8))) {
			return expectedReader.lines().collect(Collectors.joining(System.lineSeparator()));
		} catch (IOException e) {
			throw new IllegalArgumentException("파일을 읽는 중 오류 발생: " + path, e);
		}
	}
}
