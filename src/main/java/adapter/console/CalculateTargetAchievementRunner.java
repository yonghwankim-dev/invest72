package adapter.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

import adapter.InvestmentApplicationRunner;
import adapter.ui.GuidePrinter;
import application.printer.TargetAchievementResultPrinter;
import application.request.TargetAchievementRequest;
import application.resolver.KoreanStringBasedTaxableResolver;
import application.resolver.TaxableResolver;
import application.response.TargetAchievementResponse;
import application.usecase.TargetAchievementUseCase;
import domain.amount.DefaultTargetAmount;
import domain.amount.MonthlyInvestmentAmount;
import domain.amount.TargetAmount;
import domain.amount.TargetAmountReachable;
import domain.interest_rate.AnnualInterestRate;
import domain.interest_rate.InterestRate;
import domain.tax.FixedTaxRate;
import domain.tax.TaxRate;
import domain.tax.Taxable;
import domain.tax.factory.KoreanTaxableFactory;
import domain.tax.factory.TaxableFactory;
import domain.type.TaxType;

public class CalculateTargetAchievementRunner implements InvestmentApplicationRunner {

	private final PrintStream out;
	private final TargetAchievementUseCase useCase;
	private final InputStream inputStream;
	private final GuidePrinter guidePrinter;
	private final TargetAchievementResultPrinter resultPrinter;

	public CalculateTargetAchievementRunner(PrintStream out, TargetAchievementUseCase useCase,
		InputStream inputStream, GuidePrinter guidePrinter, TargetAchievementResultPrinter resultPrinter) {
		this.out = out;
		this.useCase = useCase;
		this.inputStream = inputStream;
		this.guidePrinter = guidePrinter;
		this.resultPrinter = resultPrinter;
	}

	@Override
	public void run() {
		TargetAmount targetAmount;
		TargetAmountReachable monthlyInvestment;
		InterestRate interestRate;
		Taxable taxable;
		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
			guidePrinter.printTargetAmountInputGuide();
			String targetAmountText = bufferedReader.readLine();
			targetAmount = new DefaultTargetAmount(Integer.parseInt(targetAmountText));

			guidePrinter.printMonthlyInvestmentInputGuide();
			String monthlyInvestmentText = bufferedReader.readLine();
			int monthlyInvestmentAmount = Integer.parseInt(monthlyInvestmentText);
			monthlyInvestment = new MonthlyInvestmentAmount(monthlyInvestmentAmount);

			guidePrinter.printInterestRatePercentInputGuide();
			String annualRateText = bufferedReader.readLine();
			double annualRate = toRate(Double.parseDouble(annualRateText));
			interestRate = new AnnualInterestRate(annualRate);

			guidePrinter.printTaxTypeInputGuide();
			String taxTypeText = bufferedReader.readLine();
			TaxType taxType = TaxType.from(taxTypeText);

			guidePrinter.printTaxRateInputGuide();
			String taxPercentText = bufferedReader.readLine();
			TaxRate taxRate = new FixedTaxRate(toRate(Double.parseDouble(taxPercentText)));

			TaxableFactory taxableFactory = new KoreanTaxableFactory();
			TaxableResolver taxableResolver = new KoreanStringBasedTaxableResolver(taxableFactory);
			taxable = taxableResolver.resolve(taxType, taxRate);

		} catch (IOException e) {
			out.println("[ERROR] 입력 에러: " + e.getMessage());
			return;
		}

		TargetAchievementRequest request = new TargetAchievementRequest(targetAmount, monthlyInvestment, interestRate,
			taxable);
		TargetAchievementResponse response = useCase.calTargetAchievement(request);

		resultPrinter.printResult(response);
	}

	private double toRate(double percent) {
		return percent / 100.0;
	}
}
