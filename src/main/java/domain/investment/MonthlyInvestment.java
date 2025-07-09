package domain.investment;

public interface MonthlyInvestment {
	/**
	 * month 회차에 해당하는 원금 금액을 반환합니다.
	 *
	 * @param month 회차 (1부터 시작)
	 * @return 원금 금액
	 */
	int getPrincipalAmount(int month);

	/**
	 * month 회차에 해당하는 누적 이자 금액을 반환합니다.
	 *
	 * @param month 회차 (1부터 시작)
	 * @return 이자 금액
	 */
	int getAccumulatedInterest(int month);

	/**
	 * month 회차에 해당하는 누적 세금 금액을 반환합니다.
	 *
	 * @param month 회차 (1부터 시작)
	 * @return 세금 금액
	 */
	int getAccumulatedTax(int month);

	/**
	 * month 회차에 해당하는 총 투자 금액을 반환합니다.
	 *
	 * @param month 회차 (1부터 시작)
	 * @return 총 투자 금액
	 */
	int getAccumulatedTotalProfit(int month);
}
