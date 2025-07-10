package application.usecase;

import java.time.LocalDate;

import application.time.DateProvider;

/**
 * 초기 자본금과 월 투자금액을 기반으로 목표 달성 금액을 도달하는데 걸리는 시간을 계산하는 유스케이스입니다.
 */
public class MonthlyTargetAchievementUseCase implements TargetAchievementUseCase {

	private final DateProvider dateProvider;

	public MonthlyTargetAchievementUseCase(DateProvider dateProvider) {
		this.dateProvider = dateProvider;
	}

	@Override
	public LocalDate calTargetAchievement(int targetAmount, int monthlyInvestmentAmount) {
		if (targetAmount <= 0) {
			throw new IllegalArgumentException("목표 달성 금액은 0보다 커야 합니다.");
		}
		if (monthlyInvestmentAmount <= 0) {
			throw new IllegalArgumentException("월 투자 금액은 0보다 커야 합니다.");
		}
		if (monthlyInvestmentAmount >= targetAmount) {
			return dateProvider.now();
		}
		int months = (targetAmount / monthlyInvestmentAmount) - 1;
		return dateProvider.now().plusMonths(months);
	}
}
