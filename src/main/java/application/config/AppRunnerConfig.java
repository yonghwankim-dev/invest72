package application.config;

import adapter.InvestmentApplicationRunner;
import co.invest72.achievement.console.ConsoleCalculateAchievementRunner;
import co.invest72.investment.console.ConsoleCalculateExpirationInvestmentHandler;

public interface AppRunnerConfig {
	ConsoleCalculateExpirationInvestmentHandler createCalculateInvestmentRunner();

	InvestmentApplicationRunner createCalculateMonthlyInvestmentRunner();

	ConsoleCalculateAchievementRunner createCalculateTargetAchievementRunner();
}
