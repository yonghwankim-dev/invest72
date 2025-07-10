package application.usecase;

import java.time.LocalDate;

import application.time.DateProvider;
import domain.invest_amount.TargetAmountReachable;

/**
 * 초기 자본금과 월 투자금액을 기반으로 목표 달성 금액을 도달하는데 걸리는 시간을 계산하는 유스케이스입니다.
 */
public class MonthlyTargetAchievementUseCase implements TargetAchievementUseCase {

	private final DateProvider dateProvider;

	public MonthlyTargetAchievementUseCase(DateProvider dateProvider) {
		this.dateProvider = dateProvider;
	}

	@Override
	public LocalDate calTargetAchievement(int targetAmount, TargetAmountReachable monthlyInvestmentAmount) {
		if (targetAmount <= 0) {
			throw new IllegalArgumentException("목표 달성 금액은 0보다 커야 합니다.");
		}
		int months = monthlyInvestmentAmount.calMonthsToReach(targetAmount);
		return dateProvider.now().plusMonths(months);
	}
}
