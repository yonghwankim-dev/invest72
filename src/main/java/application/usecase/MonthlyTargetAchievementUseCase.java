package application.usecase;

import java.time.LocalDate;

import application.request.TargetAchievementRequest;
import application.response.TargetAchievementResponse;
import application.time.DateProvider;
import domain.amount.DefaultTargetAmount;
import domain.amount.MonthlyInvestmentAmount;
import domain.amount.TargetAmount;
import domain.amount.TargetAmountReachable;
import domain.interest_rate.InterestRate;
import domain.tax.Taxable;

/**
 * 초기 자본금과 월 투자금액을 기반으로 목표 달성 금액을 도달하는데 걸리는 시간을 계산하는 유스케이스입니다.
 */
public class MonthlyTargetAchievementUseCase implements TargetAchievementUseCase {

	private final DateProvider dateProvider;

	public MonthlyTargetAchievementUseCase(DateProvider dateProvider) {
		this.dateProvider = dateProvider;
	}

	@Override
	public TargetAchievementResponse calTargetAchievement(TargetAchievementRequest request) {
		int initialCapital = request.initialCapital();
		TargetAmountReachable targetAmountReachable = new MonthlyInvestmentAmount(request.monthlyInvestmentAmount());
		TargetAmount targetAmount = new DefaultTargetAmount(request.targetAmount());
		InterestRate interestRate = request.interestRate();

		int months = targetAmountReachable.calMonthsToReach(initialCapital, targetAmount, interestRate);
		LocalDate achievedDate = dateProvider.calAchieveDate(months);
		int principal = initialCapital + targetAmountReachable.calPrincipal(months);
		int interest = targetAmountReachable.calInterest(targetAmount, interestRate);
		Taxable taxable = request.taxable();
		int tax = taxable.applyTax(interest);
		int afterTaxInterest = interest - tax;
		int totalProfit = principal + afterTaxInterest;

		return TargetAchievementResponse.builder()
			.achievementDate(achievedDate)
			.principal(principal)
			.interest(interest)
			.tax(tax)
			.afterTaxInterest(afterTaxInterest)
			.totalProfit(totalProfit)
			.build();
	}
}
