import adapter.InvestmentApplicationRunner;
import adapter.console.config.ConsoleAppRunnerConfig;
import application.config.AppRunnerConfig;

public class Invest72Application {
	public static void main(String[] args) {
		AppRunnerConfig appConfig = createAppRunnerConfig();
		InvestmentApplicationRunner runner = appConfig.createCalculateInvestmentRunner();
		runner.run();
	}

	private static AppRunnerConfig createAppRunnerConfig() {
		return new ConsoleAppRunnerConfig(System.in, System.out, System.err);
	}
}
