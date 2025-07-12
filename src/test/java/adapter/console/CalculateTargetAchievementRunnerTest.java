package adapter.console;

import static org.mockito.BDDMockito.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import adapter.InvestmentApplicationRunner;
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
		File inputFile = new File("src/test/resources/target_achievement_test_input1.txt");
		FileInputStream inputStream = new FileInputStream(inputFile);
		InvestmentApplicationRunner runner = new CalculateTargetAchievementRunner(out, useCase, inputStream);

		runner.run();

		String output = outputStream.toString();
		Assertions.assertTrue(output.contains("목표 달성 날짜: 2026-04-11"));
		Assertions.assertTrue(output.contains("원금: 10000000"));
		Assertions.assertTrue(output.contains("이자: 41660"));
		Assertions.assertTrue(output.contains("세금: 6415"));
		Assertions.assertTrue(output.contains("세후 이자: 35245"));
		Assertions.assertTrue(output.contains("총 수익: 10035245"));
	}
}
