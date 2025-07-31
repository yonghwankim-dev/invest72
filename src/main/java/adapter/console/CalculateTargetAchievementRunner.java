package adapter.console;

import java.io.IOException;

import adapter.InvestmentApplicationRunner;
import application.delegator.InvestmentReaderDelegator;
import application.printer.TargetAchievementResultPrinter;
import application.request.TargetAchievementRequest;
import application.response.TargetAchievementResponse;
import application.usecase.CalculateAchievement;

public class CalculateTargetAchievementRunner implements InvestmentApplicationRunner {

	private final CalculateAchievement useCase;
	private final TargetAchievementResultPrinter resultPrinter;
	private final InvestmentReaderDelegator<TargetAchievementRequest> delegator;

	public CalculateTargetAchievementRunner(CalculateAchievement useCase,
		TargetAchievementResultPrinter resultPrinter,
		InvestmentReaderDelegator<TargetAchievementRequest> delegator) {
		this.useCase = useCase;
		this.resultPrinter = resultPrinter;
		this.delegator = delegator;
	}

	@Override
	public void run() {
		try {
			TargetAchievementRequest request = delegator.readInvestmentRequest();

			TargetAchievementResponse response = useCase.calAchievement(request);

			resultPrinter.printResult(response);
		} catch (IOException e) {
			resultPrinter.printError(e);
		}
	}
}
