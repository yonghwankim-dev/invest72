package domain.investment;

import domain.tax.Taxable;

public interface TargetAchievementInvestment extends Investment {
	int calMonth(int targetAmount, int monthlyInvestmentAmount, double interestRate, Taxable taxable);
}
