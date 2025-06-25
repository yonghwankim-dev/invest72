package application;

import domain.invest_period.InvestPeriod;
import domain.invest_period.MonthlyInvestPeriod;
import domain.invest_period.YearlyInvestPeriod;
import domain.type.PeriodType;

public class KoreanStringBasedInvestPeriodFactory implements InvestPeriodFactory {

	@Override
	public InvestPeriod createBy(PeriodType periodType, int periodValue) {
		if (periodType == PeriodType.MONTH) {
			return new MonthlyInvestPeriod(periodValue);
		}
		if (periodType == PeriodType.YEAR) {
			return new YearlyInvestPeriod(periodValue);
		}
		throw new IllegalArgumentException("Invalid period type: " + periodType.getDisplayName());
	}
}
