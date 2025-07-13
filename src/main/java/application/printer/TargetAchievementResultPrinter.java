package application.printer;

import java.io.IOException;

import application.response.TargetAchievementResponse;

public interface TargetAchievementResultPrinter {
	void printResult(TargetAchievementResponse response);

	void printError(IOException e);
}
