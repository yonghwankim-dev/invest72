package co.invest72.achievement.console.output;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import co.invest72.achievement.application.CalculateAchievement;

class PrintStreamBasedTargetAchievementResultPrinterTest {

	private OutputStream outputStream;
	private TargetAchievementResultPrinter printer;

	@BeforeEach
	void setUp() {
		outputStream = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(outputStream);
		printer = new PrintStreamBasedTargetAchievementResultPrinter(out);
	}

	@Test
	void printResult() {
		CalculateAchievement.AchievementResponse response = CalculateAchievement.AchievementResponse.builder()
			.achievementDate(LocalDate.of(2023, 7, 11))
			.principal(1000000)
			.interest(50000)
			.tax(10000)
			.afterTaxInterest(40000)
			.totalProfit(1400000)
			.build();

		printer.printResult(response);

		String expectedOutput = outputStream.toString();
		assertTrue(expectedOutput.contains("목표 달성 날짜: 2023-07-11"));
		assertTrue(expectedOutput.contains("원금: 1,000,000원"));
		assertTrue(expectedOutput.contains("이자: 50,000원"));
		assertTrue(expectedOutput.contains("세금: 10,000원"));
		assertTrue(expectedOutput.contains("세후 이자: 40,000원"));
		assertTrue(expectedOutput.contains("총 수익: 1,400,000원"));
	}

	@Test
	void printError() {
		IOException exception = new IOException("Test error message");

		printer.printError(exception);

		String expectedOutput = outputStream.toString();
		assertTrue(expectedOutput.contains("[ERROR] 입력 오류: Test error message"));
	}
}
