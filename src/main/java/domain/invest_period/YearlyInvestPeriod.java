package domain.invest_period;

import domain.invest_amount.InstallmentInvestmentAmount;

public class YearlyInvestPeriod implements InvestPeriod {

	private final PeriodRange periodRange;

	public YearlyInvestPeriod(int years) {
		this(new PeriodYearRange(years));
	}

	public YearlyInvestPeriod(PeriodRange periodRange) {
		this.periodRange = periodRange;
	}

	@Override
	public int getMonths() {
		return periodRange.toMonths();
	}

	@Override
	public int getTotalPrincipal(InstallmentInvestmentAmount investmentAmount) {
		return investmentAmount.getMonthlyAmount() * getMonths();
	}

	@Override
	public double getRemainingPeriodInYears(int currentMonth) {
		if (currentMonth < 0 || currentMonth > getMonths()) {
			throw new IllegalArgumentException(
				"Current month must be between 0 and the total investment period in months.");
		}
		return (getMonths() - currentMonth) / 12.0;
	}
}
