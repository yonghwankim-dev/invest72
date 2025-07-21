package domain;

import domain.investment.ExpirationInvestment;

public interface InvestmentCalculator {
	int calPrincipal(ExpirationInvestment investment);
}
