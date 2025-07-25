package domain.amount;

import java.math.BigDecimal;

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
		return interestRate.getAnnualInterest(amount).doubleValue();
	}

	@Override
	public BigDecimal calMonthlyInterest(InterestRate interestRate) {
		return interestRate.calMonthlyInterest(amount);
	}
}
