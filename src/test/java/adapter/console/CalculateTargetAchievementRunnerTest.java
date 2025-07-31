package adapter.console;

import static org.mockito.BDDMockito.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import adapter.InvestmentApplicationRunner;
import adapter.console.ui.BufferedWriterBasedGuidePrinter;
import adapter.ui.GuidePrinter;
import application.InvestmentCalculator;
import application.TargetAchievementInvestmentCalculator;
import application.delegator.InvestmentReaderDelegator;
import application.delegator.TargetAchievementReaderDelegator;
import application.printer.PrintStreamBasedTargetAchievementResultPrinter;
import application.printer.TargetAchievementResultPrinter;
import application.reader.TargetAchievementRequestReader;
import application.request.TargetAchievementRequest;
import application.resolver.KoreanStringBasedTaxableResolver;
import application.resolver.TaxableResolver;
import application.time.DateProvider;
import application.usecase.CalculateAchievement;
import co.invest72.investment.domain.TaxableFactory;
import co.invest72.investment.domain.tax.KoreanTaxableFactory;
import util.TestFileUtils;

class CalculateTargetAchievementRunnerTest {

	private OutputStream outputStream;
	private PrintStream out;
	private CalculateAchievement useCase;

	public static Stream<Arguments> targetAchievementInputFileSource() {
		return Stream.of(
			Arguments.of("src/test/resources/target_achievement_case/case1/target_achievement_test_input1.txt",
				"src/test/resources/target_achievement_case/case1/target_achievement_test_output1.txt"),
			Arguments.of("src/test/resources/target_achievement_case/case2/target_achievement_test_input2.txt",
				"src/test/resources/target_achievement_case/case2/target_achievement_test_output2.txt")
		);
	}

	@BeforeEach
	void setUp() {
		outputStream = new ByteArrayOutputStream();
		out = new PrintStream(outputStream);
		DateProvider dateProvider = mock(DateProvider.class);
		given(dateProvider.now())
			.willReturn(LocalDate.of(2025, 7, 11));
		given(dateProvider.calAchieveDate(anyInt()))
			.willCallRealMethod();
		TaxableFactory taxableFactory = new KoreanTaxableFactory();
		TaxableResolver taxableResolver = new KoreanStringBasedTaxableResolver(taxableFactory);
		InvestmentCalculator investmentCalculator = new TargetAchievementInvestmentCalculator(taxableResolver);
		useCase = new CalculateAchievement(dateProvider, taxableResolver, investmentCalculator);
	}

	@ParameterizedTest
	@MethodSource(value = "targetAchievementInputFileSource")
	void run_shouldPrintDate(String inputFilePath, String expectedFilePath) throws FileNotFoundException {
		InputStream inputStream = new FileInputStream(inputFilePath);
		BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));
		GuidePrinter guidePrinter = new BufferedWriterBasedGuidePrinter(bufferedWriter);
		TargetAchievementResultPrinter resultPrinter = new PrintStreamBasedTargetAchievementResultPrinter(out);

		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		TargetAchievementRequestReader reader = new TargetAchievementRequestReader(bufferedReader, guidePrinter);
		InvestmentReaderDelegator<TargetAchievementRequest> delegator = new TargetAchievementReaderDelegator(reader);
		InvestmentApplicationRunner runner = new CalculateTargetAchievementRunner(useCase,
			resultPrinter, delegator);

		runner.run();

		Assertions.assertEquals(TestFileUtils.readFile(expectedFilePath), outputStream.toString());
	}
}
