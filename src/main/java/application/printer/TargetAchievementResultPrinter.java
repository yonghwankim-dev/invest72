package application.printer;

import java.io.IOException;

import co.invest72.achievement.application.CalculateAchievement;

public interface TargetAchievementResultPrinter {
	void printResult(CalculateAchievement.AchievementResponse response);

	void printError(IOException e);
}
