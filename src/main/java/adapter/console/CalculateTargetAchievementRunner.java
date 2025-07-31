package adapter.console;

import java.io.IOException;

import adapter.InvestmentApplicationRunner;
import application.delegator.InvestmentReaderDelegator;
import application.printer.TargetAchievementResultPrinter;
import co.invest72.achievement.application.CalculateAchievement;

public class CalculateTargetAchievementRunner implements InvestmentApplicationRunner {

	private final CalculateAchievement useCase;
	private final TargetAchievementResultPrinter resultPrinter;
	private final InvestmentReaderDelegator<CalculateAchievement.AchievementRequest> delegator;

	public CalculateTargetAchievementRunner(CalculateAchievement useCase,
		TargetAchievementResultPrinter resultPrinter,
		InvestmentReaderDelegator<CalculateAchievement.AchievementRequest> delegator) {
		this.useCase = useCase;
		this.resultPrinter = resultPrinter;
		this.delegator = delegator;
	}

	@Override
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
