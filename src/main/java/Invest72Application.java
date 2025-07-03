import adapter.InvestmentApplicationRunner;
import adapter.console.config.AppRunnerConfig;
import adapter.console.config.ConsoleAppConfig;

public class Invest72Application {
	public static void main(String[] args) {
		AppRunnerConfig appConfig = createAppRunnerConfig();
		InvestmentApplicationRunner runner = appConfig.createCalculateInvestmentRunner();
		runner.run();
	}

	private static AppRunnerConfig createAppRunnerConfig() {
		return new ConsoleAppConfig(System.in, System.out, System.err);
	}
}
