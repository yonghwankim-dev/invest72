package domain.amount;

import domain.interest_rate.InterestRate;

public interface TargetAmountReachable {

	/**
	 * 목표 금액에 도달하기 위해 필요한 개월 수를 계산한다.
	 * 당월을 포함한다
	 * @param targetAmount 목표 금액
	 * @param interestRate 수익률
	 * @return 개월 수
	 */
	int calMonthsToReach(TargetAmount targetAmount, InterestRate interestRate);

	int getAmount();
}
