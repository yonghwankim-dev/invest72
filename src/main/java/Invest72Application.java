import adapter.InvestmentApplicationRunner;
import adapter.console.config.AppRunnerConfig;
import adapter.console.config.ConsoleAppRunnerConfig;

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
