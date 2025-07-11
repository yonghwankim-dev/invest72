package adapter.console;

import java.io.PrintStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import adapter.InvestmentApplicationRunner;

class CalculateTargetAchievementRunnerTest {

	@Test
	void created() {
		PrintStream out = System.out;
		InvestmentApplicationRunner runner = new CalculateTargetAchievementRunner(out);

		Assertions.assertNotNull(runner);
	}

	@Test
	void run_shouldPrintDate() {
		PrintStream out = System.out;
		InvestmentApplicationRunner runner = new CalculateTargetAchievementRunner(out);

		runner.run();
	}
}
