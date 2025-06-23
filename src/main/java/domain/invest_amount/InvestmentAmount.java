package domain.invest_amount;

import domain.interest_rate.InterestRate;

public interface InvestmentAmount {
	double calAnnualInterest(InterestRate interestRate);
}
