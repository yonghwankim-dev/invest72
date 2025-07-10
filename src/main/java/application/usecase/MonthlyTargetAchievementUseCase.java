package application.usecase;

import java.time.LocalDate;

import application.time.DateProvider;
import domain.amount.TargetAmount;
import domain.amount.TargetAmountReachable;
import domain.interest_rate.InterestRate;

/**
 * 초기 자본금과 월 투자금액을 기반으로 목표 달성 금액을 도달하는데 걸리는 시간을 계산하는 유스케이스입니다.
 */
public class MonthlyTargetAchievementUseCase implements TargetAchievementUseCase {

	private final DateProvider dateProvider;

	public MonthlyTargetAchievementUseCase(DateProvider dateProvider) {
		this.dateProvider = dateProvider;
	}

	@Override
	public LocalDate calTargetAchievement(TargetAmount targetAmount, TargetAmountReachable monthlyInvestmentAmount,
		InterestRate interestRate) {
		double monthlyRate = interestRate.getMonthlyRate();
		int months = calMonths(targetAmount, monthlyInvestmentAmount, monthlyRate);
		return dateProvider.now().plusMonths(months);
	}

	private int calMonths(TargetAmount targetAmount, TargetAmountReachable monthlyInvestmentAmount,
		double monthlyRate) {
		int months = 0;
		double balance = 0;
		while (balance < targetAmount.getAmount()) {
			balance *= (1 + monthlyRate);
			balance += monthlyInvestmentAmount.getAmount();
			months++;
		}
		months = months - 1;
		return months;
	}
}
