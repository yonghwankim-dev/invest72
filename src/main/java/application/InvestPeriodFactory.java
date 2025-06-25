package application;

import domain.invest_period.InvestPeriod;
import domain.type.PeriodType;

public interface InvestPeriodFactory {
	InvestPeriod createBy(PeriodType periodType, int periodValue);
}
