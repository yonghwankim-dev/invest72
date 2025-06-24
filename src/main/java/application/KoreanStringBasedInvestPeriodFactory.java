package application;

import domain.invest_period.InvestPeriod;
import domain.invest_period.MonthlyInvestPeriod;
import domain.invest_period.YearlyInvestPeriod;

public class KoreanStringBasedInvestPeriodFactory implements InvestPeriodFactory {

	@Override
	public InvestPeriod createBy(String investPeriodName, int periodValue) {
		if (investPeriodName == null || investPeriodName.isEmpty()) {
			throw new IllegalArgumentException("investPeriodName is null or empty");
		}
		if (investPeriodName.equals("월")) {
			return new MonthlyInvestPeriod(periodValue);
		}
		if (investPeriodName.equals("년")) {
			return new YearlyInvestPeriod(periodValue);
		}
		throw new IllegalArgumentException("Unknown invest period name: " + investPeriodName);
	}
}
