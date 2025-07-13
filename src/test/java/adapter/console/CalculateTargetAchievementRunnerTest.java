package adapter.console;

import static org.mockito.BDDMockito.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import adapter.InvestmentApplicationRunner;
import adapter.console.ui.BufferedWriterBasedGuidePrinter;
import adapter.ui.GuidePrinter;
import application.printer.PrintStreamBasedTargetAchievementResultPrinter;
import application.printer.TargetAchievementResultPrinter;
import application.time.DateProvider;
import application.usecase.MonthlyTargetAchievementUseCase;
import application.usecase.TargetAchievementUseCase;

class CalculateTargetAchievementRunnerTest {

	private OutputStream outputStream;
	private PrintStream out;
	private TargetAchievementUseCase useCase;

	public static Stream<Arguments> targetAchievementInputFileSource() {
		return Stream.of(
			inputPair("target_achievement_case/case1/target_achievement_test_input1.txt",
				"target_achievement_case/case1/target_achievement_test_output1.txt"),
			inputPair("target_achievement_case/case2/target_achievement_test_input2.txt",
				"target_achievement_case/case2/target_achievement_test_output2.txt")
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

	@BeforeEach
	void setUp() {
		outputStream = new ByteArrayOutputStream();
		out = new PrintStream(outputStream);
		DateProvider dateProvider = mock(DateProvider.class);
		given(dateProvider.now())
			.willReturn(LocalDate.of(2025, 7, 11));
		given(dateProvider.calAchieveDate(anyInt()))
			.willCallRealMethod();
		useCase = new MonthlyTargetAchievementUseCase(dateProvider);
	}

	@ParameterizedTest
	@MethodSource(value = "targetAchievementInputFileSource")
	void run_shouldPrintDate(File inputFile, File expectedFile) throws FileNotFoundException {
		FileInputStream inputStream = new FileInputStream(inputFile);
		BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));
		GuidePrinter guidePrinter = new BufferedWriterBasedGuidePrinter(bufferedWriter);
		TargetAchievementResultPrinter resultPrinter = new PrintStreamBasedTargetAchievementResultPrinter(out);
		InvestmentApplicationRunner runner = new CalculateTargetAchievementRunner(useCase, inputStream,
			guidePrinter, resultPrinter);

		runner.run();

		Assertions.assertEquals(getExpectedOutput(expectedFile), outputStream.toString());
	}

	private String getExpectedOutput(File expectedFile) {
		try (BufferedReader bufferedReader = new BufferedReader(
			new InputStreamReader(new FileInputStream(expectedFile)))) {
			return bufferedReader.lines()
				.collect(Collectors.joining(System.lineSeparator(), "", System.lineSeparator()));
		} catch (IOException e) {
			throw new IllegalArgumentException("파일을 읽는 중 오류 발생: " + expectedFile, e);
		}
	}
}
