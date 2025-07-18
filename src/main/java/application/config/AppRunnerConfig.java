package application.config;

import adapter.InvestmentApplicationRunner;
import adapter.console.CalculateTargetAchievementRunner;

public interface AppRunnerConfig {
	InvestmentApplicationRunner createCalculateInvestmentRunner();

	InvestmentApplicationRunner createCalculateMonthlyInvestmentRunner();

	CalculateTargetAchievementRunner createCalculateTargetAchievementRunner();
}
