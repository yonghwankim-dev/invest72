package adapter.console;

import java.io.PrintStream;
import java.time.LocalDate;

import adapter.InvestmentApplicationRunner;

public class CalculateTargetAchievementRunner implements InvestmentApplicationRunner {

	private final PrintStream out;

	public CalculateTargetAchievementRunner(PrintStream out) {
		this.out = out;
	}

	@Override
	public void run() {
		out.println(LocalDate.of(2025, 10, 1));
	}
}
