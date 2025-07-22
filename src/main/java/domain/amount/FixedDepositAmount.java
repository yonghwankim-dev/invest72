package domain.amount;

import domain.interest_rate.InterestRate;

public class FixedDepositAmount implements LumpSumInvestmentAmount {

	private final int amount;

	public FixedDepositAmount(int amount) {
		this.amount = amount;
		if (amount < 0) {
			throw new IllegalArgumentException("investment.Investment amount must be non-negative.");
		}
	}

	@Override
	public int getDepositAmount() {
		return amount;
	}

	@Override
	public int calCompoundInterest(double totalGrowthFactor) {
		return (int)(Math.round(amount * totalGrowthFactor) - amount);
	}

	@Override
	public double calAnnualInterest(InterestRate interestRate) {
		return interestRate.getAnnualInterest(amount);
	}

	@Override
	public double calMonthlyInterest(InterestRate interestRate) {
		return interestRate.calMonthlyInterest(amount).doubleValue();
	}
}
