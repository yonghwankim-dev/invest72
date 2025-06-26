package application;

import domain.invest_period.InvestPeriod;
import domain.invest_period.MonthlyInvestPeriod;
import domain.invest_period.PeriodRange;
import domain.invest_period.YearlyInvestPeriod;
import domain.type.PeriodType;

public class KoreanStringBasedInvestPeriodFactory implements InvestPeriodFactory {

	@Override
	public InvestPeriod createBy(PeriodType periodType, PeriodRange periodRange) {
		if (periodType == PeriodType.MONTH) {
			return new MonthlyInvestPeriod(periodRange);
		}
		if (periodType == PeriodType.YEAR) {
			return new YearlyInvestPeriod(periodRange);
		}
		throw new IllegalArgumentException("Invalid period type: " + periodType);
	}
}
