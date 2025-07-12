package adapter.console;

import static org.mockito.BDDMockito.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import adapter.InvestmentApplicationRunner;
import adapter.console.ui.BufferedWriterBasedGuidePrinter;
import adapter.ui.GuidePrinter;
import application.time.DateProvider;
import application.usecase.MonthlyTargetAchievementUseCase;
import application.usecase.TargetAchievementUseCase;

class CalculateTargetAchievementRunnerTest {

	private OutputStream outputStream;
	private PrintStream out;
	private TargetAchievementUseCase useCase;

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

	@Test
	void run_shouldPrintDate() throws FileNotFoundException {
		File inputFile = new File(
			"src/test/resources/target_achievement_case/case1/target_achievement_test_input1.txt");
		FileInputStream inputStream = new FileInputStream(inputFile);
		BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));
		GuidePrinter guidePrinter = new BufferedWriterBasedGuidePrinter(bufferedWriter);
		InvestmentApplicationRunner runner = new CalculateTargetAchievementRunner(out, useCase, inputStream,
			guidePrinter);

		runner.run();

		File file = new File(
			"src/test/resources/target_achievement_case/case1/target_achievement_test_output1.txt");
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		String expectedOutput = bufferedReader.lines()
			.collect(Collectors.joining(System.lineSeparator(), "", System.lineSeparator()));
		Assertions.assertEquals(expectedOutput, outputStream.toString());
	}
}
