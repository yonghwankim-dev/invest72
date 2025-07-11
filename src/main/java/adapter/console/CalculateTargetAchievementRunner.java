package adapter.console;

import java.time.LocalDate;

import adapter.InvestmentApplicationRunner;

public class CalculateTargetAchievementRunner implements InvestmentApplicationRunner {
	@Override
	public void run() {
		System.out.println(LocalDate.of(2025, 10, 1));
	}
}
