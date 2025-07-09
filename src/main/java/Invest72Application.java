import adapter.InvestmentApplicationRunner;
import adapter.console.config.ConsoleAppRunnerConfig;
import application.config.AppRunnerConfig;

public class Invest72Application {
	public static void main(String[] args) {
		AppRunnerConfig appConfig = new ConsoleAppRunnerConfig();
		InvestmentApplicationRunner runner = appConfig.createCalculateMonthlyInvestmentRunner();
		runner.run();
	}

}
