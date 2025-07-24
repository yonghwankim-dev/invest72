package domain.investment;

import domain.amount.InstallmentInvestmentAmount;
import domain.interest_rate.InterestRate;
import domain.tax.Taxable;

public interface TargetAchievementInvestment extends Investment {
	int calMonth(int targetAmount, InstallmentInvestmentAmount investmentAmount, InterestRate interestRate,
		Taxable taxable);
}
