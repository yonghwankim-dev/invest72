package adapter.console;

import static org.mockito.BDDMockito.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import adapter.InvestmentApplicationRunner;
import application.time.DateProvider;
import application.usecase.MonthlyTargetAchievementUseCase;
import application.usecase.TargetAchievementUseCase;

class CalculateTargetAchievementRunnerTest {

	private InvestmentApplicationRunner runner;
	private OutputStream outputStream;

	@BeforeEach
	void setUp() {
		outputStream = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(outputStream);
		DateProvider dateProvider = mock(DateProvider.class);
		given(dateProvider.now())
			.willReturn(LocalDate.of(2025, 7, 11));
		given(dateProvider.calAchieveDate(anyInt()))
			.willCallRealMethod();
		TargetAchievementUseCase useCase = new MonthlyTargetAchievementUseCase(dateProvider);
		String input = String.join(System.lineSeparator(),
			"10000000", // 목표 금액
			"1000000"  // 월 투자 금액
		);
		InputStream inputStream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
		runner = new CalculateTargetAchievementRunner(out, useCase, inputStream);
	}

	@Test
	void created() {
		Assertions.assertNotNull(runner);
	}

	@Test
	void run_shouldPrintDate() {
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
