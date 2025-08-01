package co.invest72.achievement.console;

import java.io.IOException;

import application.delegator.InvestmentReaderDelegator;
import application.printer.TargetAchievementResultPrinter;
import co.invest72.achievement.application.CalculateAchievement;

public class ConsoleCalculateAchievementRunner {

	private final CalculateAchievement useCase;
	private final TargetAchievementResultPrinter resultPrinter;
	private final InvestmentReaderDelegator<CalculateAchievement.AchievementRequest> delegator;

	public ConsoleCalculateAchievementRunner(CalculateAchievement useCase,
		TargetAchievementResultPrinter resultPrinter,
		InvestmentReaderDelegator<CalculateAchievement.AchievementRequest> delegator) {
		this.useCase = useCase;
		this.resultPrinter = resultPrinter;
		this.delegator = delegator;
	}

	public void run() {
		try {
			CalculateAchievement.AchievementRequest request = delegator.readInvestmentRequest();

			CalculateAchievement.AchievementResponse response = useCase.calAchievement(request);

			resultPrinter.printResult(response);
		} catch (IOException e) {
			resultPrinter.printError(e);
		}
	}
}
