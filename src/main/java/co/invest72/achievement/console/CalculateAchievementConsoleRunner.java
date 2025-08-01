package co.invest72.achievement.console;

import static co.invest72.achievement.application.CalculateAchievement.*;

import java.io.IOException;

import co.invest72.achievement.application.CalculateAchievement;
import co.invest72.achievement.console.input.delegator.TargetAchievementReaderDelegator;
import co.invest72.achievement.console.output.TargetAchievementResultPrinter;

public class CalculateAchievementConsoleRunner {

	private final CalculateAchievement useCase;
	private final TargetAchievementResultPrinter resultPrinter;
	private final TargetAchievementReaderDelegator delegator;

	public CalculateAchievementConsoleRunner(TargetAchievementResultPrinter resultPrinter,
		TargetAchievementReaderDelegator delegator, CalculateAchievement useCase) {
		this.useCase = useCase;
		this.resultPrinter = resultPrinter;
		this.delegator = delegator;
	}

	public void run() {
		try {
			AchievementRequest request = delegator.readRequest();

			AchievementResponse response = useCase.calAchievement(request);

			resultPrinter.printResult(response);
		} catch (IOException e) {
			resultPrinter.printError(e);
		}
	}
}
