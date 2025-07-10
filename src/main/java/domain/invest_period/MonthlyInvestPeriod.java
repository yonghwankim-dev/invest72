package domain.invest_period;

import domain.amount.InstallmentInvestmentAmount;

public class MonthlyInvestPeriod implements InvestPeriod {
	private final PeriodRange periodRange;

	public MonthlyInvestPeriod(int months) {
		this(new PeriodMonthsRange(months));
	}

	public MonthlyInvestPeriod(PeriodRange periodRange) {
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
