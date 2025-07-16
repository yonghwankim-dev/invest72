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
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

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
import application.factory.MonthlyInvestmentFactory;
import application.factory.UseCaseFactory;
import application.printer.InvestmentResultPrinter;
import application.printer.PrintStreamBasedInvestmentResultPrinter;
import application.reader.CalculateInvestmentRequestReader;
import application.reader.InvestReader;
import application.reader.impl.BufferedReaderBasedInvestReader;
import application.registry.InvestmentAmountReaderStrategyRegistry;
import application.registry.MapBasedInvestmentAmountReaderStrategyRegistry;
import application.request.CalculateInvestmentRequest;
import application.strategy.FixedDepositAmountReaderStrategy;
import application.strategy.InstallmentSavingAmountReaderStrategy;
import application.strategy.InvestmentAmountReaderStrategy;
import domain.investment.Investment;
import domain.investment.MonthlyInvestment;
import domain.type.InvestmentType;

class CalculateInvestmentRunnerTest {

	private UseCaseFactory useCaseFactory;
	private ByteArrayOutputStream outputStream;
	private GuidePrinter guidePrinter;
	private InputStream in;
	private InvestmentApplicationRunner runner;
	private InvestmentReaderDelegator<CalculateInvestmentRequest> investmentReaderDelegator;
	private PrintStream err;
	private InvestmentRequestBuilder requestBuilder;
	private BufferedReader reader;
	private InvestReader investReader;
	private InvestmentAmountReaderStrategyRegistry amountReaderStrategyRegistry;
	private InvestmentResultPrinter investmentResultPrinter;
	private CalculateInvestmentRequestReader calculateInvestmentRequestReader;

	public static Stream<Arguments> inputFileSource() {
		return Stream.of(
			inputPair("test_input1.txt", "expected_output1.txt"),
			inputPair("test_input2.txt", "expected_output2.txt"),
			inputPair("test_input3.txt", "expected_output3.txt"),
			inputPair("test_input4.txt", "expected_output4.txt")
		);
	}

	private static Arguments inputPair(String inputFileName, String expectedFileName) {
		return Arguments.of(
			toTestFile(inputFileName),
			toTestFile(expectedFileName)
		);
	}

	private static File toTestFile(String fileName) {
		return new File("src/test/resources/" + fileName);
	}

	private String getExpectedFileContent(File file) {
		try (BufferedReader expectedReader = new BufferedReader(
			new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
			return expectedReader.lines()
				.collect(Collectors.joining(System.lineSeparator(), "", System.lineSeparator()));
		} catch (IOException e) {
			throw new IllegalArgumentException("파일을 읽는 중 오류 발생: " + file, e);
		}
	}

	@BeforeEach
	void setUp() {
		InvestmentFactory<Investment> investmentFactory = new DefaultInvestmentFactory();
		InvestmentFactory<MonthlyInvestment> monthlyInvestmentFactory = new MonthlyInvestmentFactory();
		useCaseFactory = new InvestmentUseCaseFactory(investmentFactory, monthlyInvestmentFactory);
		PrintStream out = System.out;
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(out);
		BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
		in = System.in;
		outputStream = new ByteArrayOutputStream();
		PrintStream printStream = new PrintStream(outputStream);
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
		calculateInvestmentRequestReader = new CalculateInvestmentRequestReader(reader, guidePrinter);
		investmentReaderDelegator = new CalculateInvestmentReaderDelegator(requestBuilder, amountReaderStrategyRegistry,
			calculateInvestmentRequestReader
		);
		investmentResultPrinter = new PrintStreamBasedInvestmentResultPrinter(printStream);
		runner = new CalculateInvestmentRunner(
			err, useCaseFactory,
			investmentReaderDelegator,
			investmentResultPrinter
		);
	}

	private void assertOutput(String expected, String output) {
		assertEquals(expected, output);
	}

	@Test
	void created() {
		assertNotNull(runner);
	}

	@ParameterizedTest
	@MethodSource(value = "inputFileSource")
	void shouldPrintAmount(File inputFile, File expectedFile) throws FileNotFoundException {
		in = new FileInputStream(inputFile);
		reader = new BufferedReader(new InputStreamReader(in));
		investReader = new BufferedReaderBasedInvestReader(reader, guidePrinter);
		investmentReaderDelegator = new CalculateInvestmentReaderDelegator(requestBuilder, amountReaderStrategyRegistry,
			calculateInvestmentRequestReader
		);
		runner = new CalculateInvestmentRunner(
			err, useCaseFactory,
			investmentReaderDelegator,
			investmentResultPrinter
		);

		runner.run();

		String output = outputStream.toString(StandardCharsets.UTF_8);
		String expected = getExpectedFileContent(expectedFile);
		assertOutput(expected, output);
	}
}
