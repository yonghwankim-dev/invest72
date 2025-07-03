package application.config;

import adapter.InvestmentApplicationRunner;

public interface AppRunnerConfig {
	InvestmentApplicationRunner createCalculateInvestmentRunner();
}
