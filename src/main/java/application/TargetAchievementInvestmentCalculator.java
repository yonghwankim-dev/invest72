package application;

import domain.amount.InstallmentInvestmentAmount;
import domain.interest_rate.InterestRate;
import domain.tax.Taxable;

public class TargetAchievementInvestmentCalculator implements InvestmentCalculator {

	private final int targetAmount;
	private final InstallmentInvestmentAmount investmentAmount;
	private final InterestRate interestRate;
	private final Taxable taxable;

	public TargetAchievementInvestmentCalculator(int targetAmount, InstallmentInvestmentAmount investmentAmount,
		InterestRate interestRate, Taxable taxable) {
		this.targetAmount = targetAmount;
		this.investmentAmount = investmentAmount;
		this.interestRate = interestRate;
		this.taxable = taxable;
	}

	@Override
	public int calMonth() {
		return 10;
	}
}
