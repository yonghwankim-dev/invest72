package application;

import domain.invest_period.InvestPeriod;
import domain.invest_period.PeriodRange;
import domain.type.PeriodType;

public interface InvestPeriodFactory {
	InvestPeriod createBy(PeriodType periodType, PeriodRange periodRange);
}
