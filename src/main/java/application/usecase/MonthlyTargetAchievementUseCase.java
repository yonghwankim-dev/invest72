package application.usecase;

import java.time.LocalDate;

import application.request.TargetAchievementRequest;
import application.response.TargetAchievementResponse;
import application.time.DateProvider;
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
	public TargetAchievementResponse calTargetAchievement(TargetAmount targetAmount,
		TargetAmountReachable monthlyInvestmentAmount, InterestRate interestRate, Taxable taxable) {
		int months = monthlyInvestmentAmount.calMonthsToReach(targetAmount, interestRate);

		LocalDate achievedDate = dateProvider.calAchieveDate(months);
		int principal = monthlyInvestmentAmount.calPrincipal(months);
		int interest = monthlyInvestmentAmount.calInterest(targetAmount, interestRate);
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

	@Override
	public TargetAchievementResponse calTargetAchievement(TargetAchievementRequest request) {
		int months = request.monthlyInvestmentAmount().calMonthsToReach(request.targetAmount(), request.interestRate());

		LocalDate achievedDate = dateProvider.calAchieveDate(months);
		int principal = request.monthlyInvestmentAmount().calPrincipal(months);
		int interest = request.monthlyInvestmentAmount().calInterest(request.targetAmount(), request.interestRate());
		int tax = request.taxable().applyTax(interest);
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
