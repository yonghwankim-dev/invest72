package domain.invest_period;

import domain.invest_amount.InstallmentInvestmentAmount;

public class MonthlyInvestPeriod implements InvestPeriod {
	private final int months;

	public MonthlyInvestPeriod(int months) {
		this.months = months;
		if (this.months < 0) {
			throw new IllegalArgumentException("investment.Investment period must be greater than zero.");
		}
		if (this.months > 999){
			throw new IllegalArgumentException("investment.Investment period must not be greater than 999 months.");
		}
	}

	@Override
	public int getMonths() {
		return months;
	}

	@Override
	public int getTotalPrincipal(InstallmentInvestmentAmount investmentAmount) {
		return investmentAmount.getMonthlyAmount() * months;
	}

	/**
	 * 이 메서드는 전체 투자 기간에서 현재까지 지난 개월 수를 빼서 남은 투자 기간(개월)을 구하고, 이를 12로 나누어 남은 투자 기간을 년 단위(double)로 반환합니다.
	 * 예를 들어, investPeriod.getMonths()가 36개월이고, currentMonth가 18이면 (36 - 18) / 12.0 = 1.5년이 됩니다.
	 * @param currentMonth 현재까지 지난 개월 수
	 * @return 남은 투자 기간(년 단위)
	 */
	@Override
	public double getRemainingPeriodInYears(int currentMonth) {
		if (currentMonth < 0 || currentMonth > months) {
			throw new IllegalArgumentException("Current month must be between 0 and the total investment period in months.");
		}
		return (months - currentMonth) / 12.0;
	}
}
