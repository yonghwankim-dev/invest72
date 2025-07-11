package adapter.console;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import adapter.InvestmentApplicationRunner;

class CalculateTargetAchievementRunnerTest {

	private InvestmentApplicationRunner runner;
	private OutputStream outputStream;

	@BeforeEach
	void setUp() {
		outputStream = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(outputStream);
		runner = new CalculateTargetAchievementRunner(out);
	}

	@Test
	void created() {
		Assertions.assertNotNull(runner);
	}

	@Test
	void run_shouldPrintDate() {
		runner.run();

		String output = outputStream.toString();
		Assertions.assertEquals("""
			목표 달성 날짜: 2026-04-11
			원금: 10000000
			이자: 41660
			세금: 6415
			세후 이자: 35245
			총 수익: 10035245
			""", output);
	}
}
