package domain.amount;

import java.math.BigDecimal;

import domain.interest_rate.InterestRate;

public interface InvestmentAmount {
	double calAnnualInterest(InterestRate interestRate);

	BigDecimal calMonthlyInterest(InterestRate interestRate);
}
