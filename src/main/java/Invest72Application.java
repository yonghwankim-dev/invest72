import adapter.console.config.ConsoleAppRunnerConfig;
import application.config.AppRunnerConfig;
import co.invest72.achievement.console.ConsoleCalculateAchievementRunner;

public class Invest72Application {
	public static void main(String[] args) {
		AppRunnerConfig appConfig = new ConsoleAppRunnerConfig();
		ConsoleCalculateAchievementRunner runner = appConfig.createCalculateTargetAchievementRunner();
		runner.run();
	}
}
