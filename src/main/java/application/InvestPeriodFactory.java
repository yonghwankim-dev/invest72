package application;

import domain.invest_period.InvestPeriod;

public interface InvestPeriodFactory {
	InvestPeriod createBy(String investPeriodName, int periodValue);
}
