package co.invest72.investment.domain.amount;

import java.math.BigDecimal;

import co.invest72.investment.domain.InterestRate;
import co.invest72.investment.domain.InvestmentAmount;

public class MonthlyAmount implements InvestmentAmount {

	private final int amount;

	public MonthlyAmount(int amount) {
		this.amount = amount;
		if (amount <= 0) {
			throw new IllegalArgumentException("Monthly amount must be positive.");
		}
	}

	@Override
	public double calAnnualInterest(InterestRate interestRate) {
		return 0;
	}

	@Override
	public BigDecimal calMonthlyInterest(InterestRate interestRate) {
		return interestRate.calMonthlyInterest(amount);
	}
}
