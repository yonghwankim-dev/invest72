package application.usecase;

import java.time.LocalDate;

import domain.invest_amount.TargetDurationCalculator;

/**
 * 다양한 투자 방법(예: 월 납입, 자본금 등) 등을 통해서 목표 달성 금액을 도달하는데 걸리는 시간을 계산하는 유스케이스입니다.
 */
public interface TargetAchievementUseCase {

	LocalDate calTargetAchievement(int targetAmount, TargetDurationCalculator monthlyInvestmentAmount);
}
