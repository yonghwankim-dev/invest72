package adapter.console;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import adapter.InvestmentApplicationRunner;

class CalculateTargetAchievementRunnerTest {

	@Test
	void created() {
		InvestmentApplicationRunner runner = new CalculateTargetAchievementRunner();

		Assertions.assertNotNull(runner);
	}

	@Test
	void run_shouldPrintDate() {
		InvestmentApplicationRunner runner = new CalculateTargetAchievementRunner();

		runner.run();

	}
}
