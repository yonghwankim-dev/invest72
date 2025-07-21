package domain;

import domain.investment.Investment;

public class ExpirationPeriodBasedInvestmentCalculator implements PeriodBasedInvestmentCalculator {
	@Override
	public int calPrincipal(Investment investment) {
		return investment.getPrincipalAmount();
	}
}
