package adapter.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

import adapter.InvestmentApplicationRunner;
import application.request.TargetAchievementRequest;
import application.response.TargetAchievementResponse;
import application.usecase.TargetAchievementUseCase;
import domain.amount.DefaultTargetAmount;
import domain.amount.MonthlyInvestmentAmount;
import domain.amount.TargetAmount;
import domain.amount.TargetAmountReachable;
import domain.interest_rate.AnnualInterestRate;
import domain.tax.FixedTaxRate;
import domain.tax.StandardTax;
import domain.tax.Taxable;

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
		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
			out.println("목표 금액을 입력하세요 (예: 10000000): ");
			String targetAmountText = bufferedReader.readLine();
			targetAmount = new DefaultTargetAmount(Integer.parseInt(targetAmountText));

			out.println("월 투자 금액을 입력하세요 (예: 1000000): ");
			String monthlyInvestmentText = bufferedReader.readLine();
			int monthlyInvestmentAmount = Integer.parseInt(monthlyInvestmentText);
			monthlyInvestment = new MonthlyInvestmentAmount(monthlyInvestmentAmount);

		} catch (IOException e) {
			out.println("[ERROR] 입력 에러: " + e.getMessage());
			return;
		}
		AnnualInterestRate interestRate = new AnnualInterestRate(0.05);
		Taxable taxable = new StandardTax(new FixedTaxRate(0.154));
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
