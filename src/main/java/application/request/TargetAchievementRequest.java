package application.request;

import domain.interest_rate.InterestRate;
import domain.tax.Taxable;

public record TargetAchievementRequest(int initialCapital, int targetAmount,
									   int monthlyInvestmentAmount, InterestRate interestRate,
									   Taxable taxable) {

	public TargetAchievementRequest(int targetAmount, int monthlyInvestmentAmount,
		InterestRate interestRate, Taxable taxable) {
		this(0, targetAmount, monthlyInvestmentAmount, interestRate, taxable);
	}
}
