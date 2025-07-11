package application.usecase;

import java.time.LocalDate;

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
		return new TargetAchievementResponse(achievedDate, principal, interest, tax);
	}
}
