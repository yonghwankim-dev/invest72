package co.invest72.investment.domain;

public interface RemainingPeriodProvider {
	/**
	 * 이 메서드는 전체 투자 기간에서 현재까지 지난 개월 수를 빼서 남은 투자 기간(개월)을 구하고, 이를 12로 나누어 남은 투자 기간을 년 단위(double)로 반환합니다.
	 * 예를 들어, investPeriod.getMonths()가 36개월이고, currentMonth가 18이면 (36 - 18) / 12.0 = 1.5년이 됩니다.
	 * @param currentMonth 현재까지 지난 개월 수
	 * @return 남은 투자 기간(년 단위)
	 */
	double calRemainingPeriodInYears(int currentMonth);

	/**
	 * 투자 기간의 마지막 회차를 반환합니다.
	 *
	 * @return 투자 기간의 마지막 회차 (1부터 시작)
	 */
	int getFinalMonth();
}
