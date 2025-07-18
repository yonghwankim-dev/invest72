package adapter.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
		int targetAmount;
		int monthlyInvestment;
		double interestRate;
		String taxType;
		double taxRate;
		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
			guidePrinter.printTargetAmountInputGuide();
			String targetAmountText = bufferedReader.readLine();
			targetAmount = Integer.parseInt(targetAmountText);

			guidePrinter.printMonthlyInvestmentInputGuide();
			String monthlyInvestmentText = bufferedReader.readLine();
			monthlyInvestment = Integer.parseInt(monthlyInvestmentText);

			guidePrinter.printInterestPercentInputGuide();
			String annualRateText = bufferedReader.readLine();
			interestRate = toRate(Double.parseDouble(annualRateText));

			guidePrinter.printTaxTypeInputGuide();
			taxType = bufferedReader.readLine();

			guidePrinter.printTaxRateInputGuide();
			String taxPercentText = bufferedReader.readLine();
			taxRate = toRate(Double.parseDouble(taxPercentText));

		} catch (IOException e) {
			resultPrinter.printError(e);
			return;
		}

		TargetAchievementRequest request = new TargetAchievementRequest(targetAmount, monthlyInvestment, interestRate,
			taxType, taxRate);
		TargetAchievementResponse response = useCase.calTargetAchievement(request);

		resultPrinter.printResult(response);
	}

	private double toRate(double percent) {
		return percent / 100.0;
	}
}
