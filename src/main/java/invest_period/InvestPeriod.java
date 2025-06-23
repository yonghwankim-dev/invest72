package invest_period;

import invest_amount.InstallmentInvestmentAmount;

public interface InvestPeriod {
	int getMonths();

	int getTotalPrincipal(InstallmentInvestmentAmount investmentAmount);

	/**
	 * 이 메서드는 전체 투자 기간에서 현재까지 지난 개월 수를 빼서 남은 투자 기간(년 단위)을 반환합니다.
	 * @param currentMonth 현재까지 지난 개월 수
	 * @return 남은 투자 기간(년 단위)
	 */
	double getRemainingPeriodInYears(int currentMonth);
}
