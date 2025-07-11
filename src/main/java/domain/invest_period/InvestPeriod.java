package domain.invest_period;

import domain.amount.InstallmentInvestmentAmount;

public interface InvestPeriod {
	int getMonths();

	int getTotalPrincipal(InstallmentInvestmentAmount investmentAmount);
}
