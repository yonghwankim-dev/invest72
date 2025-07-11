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
		Assertions.assertEquals("2025-10-01\n", output);
	}
}
