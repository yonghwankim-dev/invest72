package application.config;

import adapter.InvestmentApplicationRunner;
import co.invest72.achievement.console.ConsoleCalculateAchievementRunner;
import co.invest72.investment.console.ConsoleCalculateExpirationInvestmentRunner;

public interface AppRunnerConfig {
	ConsoleCalculateExpirationInvestmentRunner createCalculateInvestmentRunner();

	InvestmentApplicationRunner createCalculateMonthlyInvestmentRunner();

	ConsoleCalculateAchievementRunner createCalculateTargetAchievementRunner();
}
