package domain.amount;

import domain.interest_rate.InterestRate;

public class YearlyInstallmentInvestmentAmount implements InstallmentInvestmentAmount {
	private final int amount;

	public YearlyInstallmentInvestmentAmount(int amount) {
		this.amount = amount;
	}

	@Override
	public int getMonthlyAmount() {
		return amount / 12;
	}

	@Override
	public double calAnnualInterest(InterestRate interestRate) {
		return interestRate.getAnnualInterest(amount);
	}

	@Override
	public double calMonthlyInterest(InterestRate interestRate) {
		return interestRate.calMonthlyInterest(amount);
	}
}
