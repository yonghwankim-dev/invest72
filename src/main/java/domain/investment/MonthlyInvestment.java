package domain.investment;

public interface MonthlyInvestment {
	/**
	 * month 회차에 해당하는 누적 원금 금액을 반환합니다.
	 *
	 * @param month 회차 (1부터 시작)
	 * @return 원금 금액
	 */
	int getPrincipal(int month);

	/**
	 * month 회차에 해당하는 누적 이자 금액을 반환합니다.
	 *
	 * @param month 회차 (1부터 시작)
	 * @return 이자 금액
	 */
	int getInterest(int month);

	/**
	 * month 회차에 해당하는 누적 세금 금액을 반환합니다.
	 *
	 * @param month 회차 (1부터 시작)
	 * @return 세금 금액
	 */
	int getTax(int month);

	/**
	 * month 회차에 해당하는 누적 총 투자 금액을 반환합니다.
	 *
	 * @param month 회차 (1부터 시작)
	 * @return 총 투자 금액
	 */
	int getAccumulatedTotalProfit(int month);

	/**
	 * 투자 기간의 마지막 월을 반환합니다
	 * <p>
	 * 예를 들어 투자 기간이 1년이면 12를 반환한다.
	 * @return 마지막 월 (1부터 시작)
	 */
	int getFinalMonth();
}
