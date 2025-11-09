package co.invest72.investment.domain;

import co.invest72.investment.domain.InstallmentInvestmentAmount;

public interface InvestPeriod {
	int getMonths();

	int getTotalPrincipal(InstallmentInvestmentAmount investmentAmount);
}
