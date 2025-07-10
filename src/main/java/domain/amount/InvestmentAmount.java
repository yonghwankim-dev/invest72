package domain.amount;

import domain.interest_rate.InterestRate;

public interface InvestmentAmount {
	double calAnnualInterest(InterestRate interestRate);

	int calMonthlyInterest(InterestRate interestRate);
}
