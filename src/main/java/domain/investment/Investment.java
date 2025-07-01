package domain.investment;

import domain.invest_amount.InvestmentAmount;

public interface Investment {
	int getAmount();

	default int getAmount(InvestmentAmount investmentAmount) {
		return 0;
	}
}
