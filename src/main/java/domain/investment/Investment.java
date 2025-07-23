package domain.investment;

public interface Investment {
	/**
	 * 만기까지의 원금 금액을 반환합니다.
	 *
	 * @return 원금 금액
	 */
	int getPrincipal();

	/**
	 * 지정된 월 회차(month)까지의 누적 원금 금액을 반환합니다.
	 *
	 * @param month 회차 (1부터 시작)
	 * @return 원금 금액
	 */
	int getPrincipal(int month);

	/**
	 * 만기까지의 이자 금액을 반환합니다.
	 * <p>
	 * 해당 금액은 세전 이자 금액입니다.
	 * </p>
	 * @return 이자 금액
	 */
	int getInterest();

	/**
	 * 지정된 월 회차(month)까지의 누적 이자 금액을 반환합니다.
	 * <p>
	 * 해당 금액은 세전 이자 금액입니다.
	 * </p>
	 * @param month 회차 (1부터 시작)
	 * @return 이자 금액
	 */
	int getInterest(int month);

	/**
	 * 만기까지의 세금 금액을 반환합니다.
	 * @return 세금 금액
	 */
	int getTax();

	/**
	 * 지정된 월 회차(month)까지의 누적 세금 금액을 반환합니다.
	 *
	 * @param month 회차 (1부터 시작)
	 * @return 세금 금액
	 */
	int getTax(int month);

	/**
	 * 만기까지의 총 투자 금액을 반환합니다.
	 * <p>
	 * 해당 금액은 원금 + 이자 - 세금 입니다.
	 * </p>
	 * @return 총 투자 금액
	 */
	int getTotalProfit();

	/**
	 * 지정된 월 회차(month)까지의 누적 총 투자 금액을 반환합니다.
	 * <p>
	 * 해당 금액은 원금 + 이자 - 세금 입니다.
	 * </p>
	 *
	 * @param month 회차 (1부터 시작)
	 * @return 총 투자 금액
	 */
	int getTotalProfit(int month);

	/**
	 * 투자 기간의 마지막 월을 반환합니다
	 * <p>
	 * 예를 들어 투자 기간이 1년이면 12를 반환한다.
	 * </p>
	 * @return 마지막 월 (1부터 시작)
	 */
	int getFinalMonth();
}
