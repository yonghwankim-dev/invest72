package domain.invest_amount;

public interface TargetDurationCalculator {

	/**
	 * 목표 금액에 도달하기 위해 필요한 개월 수를 계산합니다.
	 * @param targetAmount 목표 금액
	 * @return 목표 금액에 도달하기 위해 필요한 개월 수(month)
	 */
	int calMonthsToReachTarget(int targetAmount);
}
