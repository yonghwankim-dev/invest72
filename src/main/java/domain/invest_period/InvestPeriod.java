package domain.invest_period;

import domain.invest_amount.InstallmentInvestmentAmount;

public interface InvestPeriod {
	int getMonths();

	int getTotalPrincipal(InstallmentInvestmentAmount investmentAmount);
}
