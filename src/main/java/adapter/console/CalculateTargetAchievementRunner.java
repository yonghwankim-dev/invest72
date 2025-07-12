package adapter.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

import adapter.InvestmentApplicationRunner;
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

	public CalculateTargetAchievementRunner(PrintStream out, TargetAchievementUseCase useCase,
		InputStream inputStream) {
		this.out = out;
		this.useCase = useCase;
		this.inputStream = inputStream;
	}

	@Override
	public void run() {
		TargetAmount targetAmount;
		TargetAmountReachable monthlyInvestment;
		InterestRate interestRate;
		Taxable taxable;
		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
			out.println("목표 금액을 입력하세요 (예: 10000000): ");
			String targetAmountText = bufferedReader.readLine();
			targetAmount = new DefaultTargetAmount(Integer.parseInt(targetAmountText));

			out.println("월 투자 금액을 입력하세요 (예: 1000000): ");
			String monthlyInvestmentText = bufferedReader.readLine();
			int monthlyInvestmentAmount = Integer.parseInt(monthlyInvestmentText);
			monthlyInvestment = new MonthlyInvestmentAmount(monthlyInvestmentAmount);

			out.println("연 수익률을 입력하세요 (예: 0.05): ");
			String annualRateText = bufferedReader.readLine();
			double annualRate = Double.parseDouble(annualRateText);
			interestRate = new AnnualInterestRate(annualRate);

			out.println("세금 타입을 입력하세요 (예: 일반과세, 비과세, 세금우대)");
			String taxTypeText = bufferedReader.readLine();
			TaxType taxType = TaxType.from(taxTypeText);

			out.println("세금 비율을 입력하세요 (예: 0.154): ");
			String taxRateText = bufferedReader.readLine();
			TaxRate taxRate = new FixedTaxRate(Double.parseDouble(taxRateText));

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

		out.println("목표 달성 날짜: " + response.getAchievedDate());
		out.println("원금: " + response.getPrincipal());
		out.println("이자: " + response.getInterest());
		out.println("세금: " + response.getTax());
		out.println("세후 이자: " + response.getAfterTaxInterest());
		out.println("총 수익: " + response.getTotalProfit());
	}
}
