package co.invest72.achievement.console;

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

import co.invest72.achievement.application.CalculateAchievement;
import co.invest72.achievement.console.input.delegator.TargetAchievementReaderDelegator;
import co.invest72.achievement.console.input.reader.TargetAchievementRequestReader;
import co.invest72.achievement.console.output.PrintStreamBasedTargetAchievementResultPrinter;
import co.invest72.achievement.console.output.TargetAchievementResultPrinter;
import co.invest72.achievement.domain.AchievementDateCalculator;
import co.invest72.achievement.domain.time.AchievementInvestmentCalculator;
import co.invest72.investment.console.output.guide.BufferedWriterBasedGuidePrinter;
import co.invest72.investment.console.output.guide.GuidePrinter;
import co.invest72.investment.domain.TaxableFactory;
import co.invest72.investment.domain.TaxableResolver;
import co.invest72.investment.domain.tax.KoreanTaxableFactory;
import co.invest72.investment.domain.tax.resolver.KoreanStringBasedTaxableResolver;
import util.TestFileUtils;

class CalculateAchievementConsoleRunnerTest {

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
		AchievementDateCalculator achievementDateCalculator = mock(AchievementDateCalculator.class);
		given(achievementDateCalculator.now())
			.willReturn(LocalDate.of(2025, 7, 11));
		given(achievementDateCalculator.addMonth(anyInt()))
			.willCallRealMethod();
		TaxableFactory taxableFactory = new KoreanTaxableFactory();
		TaxableResolver taxableResolver = new KoreanStringBasedTaxableResolver(taxableFactory);
		AchievementInvestmentCalculator investmentCalculator = new AchievementInvestmentCalculator(taxableResolver);
		useCase = new CalculateAchievement(achievementDateCalculator, taxableResolver, investmentCalculator);
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
		TargetAchievementReaderDelegator delegator = new TargetAchievementReaderDelegator(reader);
		CalculateAchievementConsoleRunner runner = new CalculateAchievementConsoleRunner(resultPrinter, delegator,
			useCase
		);

		runner.run();

		Assertions.assertEquals(TestFileUtils.readFile(expectedFilePath), outputStream.toString());
	}
}
