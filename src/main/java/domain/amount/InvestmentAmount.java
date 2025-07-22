package domain.amount;

import domain.interest_rate.InterestRate;

public interface InvestmentAmount {
	double calAnnualInterest(InterestRate interestRate);

	double calMonthlyInterest(InterestRate interestRate);
}
