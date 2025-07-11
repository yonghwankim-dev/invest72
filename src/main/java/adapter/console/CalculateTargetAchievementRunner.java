package adapter.console;

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

	public CalculateTargetAchievementRunner(PrintStream out, TargetAchievementUseCase useCase) {
		this.out = out;
		this.useCase = useCase;
	}

	@Override
	public void run() {
		TargetAmount targetAmount = new DefaultTargetAmount(10_000_000);
		TargetAmountReachable monthlyInvestment = new MonthlyInvestmentAmount(1_000_000);
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
