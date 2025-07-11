package application.usecase;

import application.request.TargetAchievementRequest;
import application.response.TargetAchievementResponse;
import domain.amount.TargetAmount;
import domain.amount.TargetAmountReachable;
import domain.interest_rate.InterestRate;
import domain.tax.Taxable;

/**
 * 다양한 투자 방법(예: 월 납입, 자본금 등) 등을 통해서 목표 달성 금액을 도달하는데 걸리는 시간을 계산하는 유스케이스입니다.
 */
public interface TargetAchievementUseCase {

	/**
	 * 목표 달성 금액을 도달하는데 걸리는 시간을 계산합니다.
	 * 당월에 투자하는 것으로 가정합니다.
	 * 예를 들어 2025년 1월 1일에 월 투자금액을 투자한다면 1월을 포함한다.
	 *
	 * @param targetAmount            목표 달성 금액
	 * @param monthlyInvestmentAmount 월 투자 금액을 나타내는 객체
	 * @param interestRate            이자율
	 * @return 목표 달성 금액을 도달하는 날짜
	 */
	TargetAchievementResponse calTargetAchievement(TargetAmount targetAmount,
		TargetAmountReachable monthlyInvestmentAmount, InterestRate interestRate, Taxable taxable);

	TargetAchievementResponse calTargetAchievement(TargetAchievementRequest request);
}
