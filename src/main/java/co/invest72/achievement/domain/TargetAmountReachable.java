package co.invest72.achievement.domain;

import co.invest72.achievement.domain.amount.TargetAmount;
import co.invest72.investment.domain.InterestRate;

public interface TargetAmountReachable {

	/**
	 * 목표 금액에 도달하기 위해 필요한 개월 수를 계산한다.
	 * 당월을 포함한다
	 * @param targetAmount 목표 금액
	 * @param interestRate 수익률
	 * @return 개월 수
	 */
	int calMonthsToReach(TargetAmount targetAmount, InterestRate interestRate);

	int calMonthsToReach(int initialCapital, TargetAmount targetAmount, InterestRate interestRate);

	int getAmount();

	int calPrincipal(int months);

	int calInterest(TargetAmount targetAmount, InterestRate interestRate);
}
