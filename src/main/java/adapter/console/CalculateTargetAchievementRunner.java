package adapter.console;

import java.io.IOException;
import java.io.InputStream;

import adapter.InvestmentApplicationRunner;
import adapter.ui.GuidePrinter;
import application.delegator.InvestmentReaderDelegator;
import application.printer.TargetAchievementResultPrinter;
import application.request.TargetAchievementRequest;
import application.response.TargetAchievementResponse;
import application.usecase.TargetAchievementUseCase;

public class CalculateTargetAchievementRunner implements InvestmentApplicationRunner {

	private final TargetAchievementUseCase useCase;
	private final InputStream inputStream;
	private final GuidePrinter guidePrinter;
	private final TargetAchievementResultPrinter resultPrinter;
	private final InvestmentReaderDelegator<TargetAchievementRequest> delegator;

	public CalculateTargetAchievementRunner(TargetAchievementUseCase useCase,
		InputStream inputStream, GuidePrinter guidePrinter, TargetAchievementResultPrinter resultPrinter,
		InvestmentReaderDelegator<TargetAchievementRequest> delegator) {
		this.useCase = useCase;
		this.inputStream = inputStream;
		this.guidePrinter = guidePrinter;
		this.resultPrinter = resultPrinter;
		this.delegator = delegator;
	}

	@Override
	public void run() {
		TargetAchievementRequest request = null;
		try {
			request = delegator.readInvestmentRequest();
		} catch (IOException e) {
			resultPrinter.printError(e);
		}
		TargetAchievementResponse response = useCase.calTargetAchievement(request);

		resultPrinter.printResult(response);
	}
}
