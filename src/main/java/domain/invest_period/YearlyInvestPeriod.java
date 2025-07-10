package domain.invest_period;

import domain.amount.InstallmentInvestmentAmount;

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
}
