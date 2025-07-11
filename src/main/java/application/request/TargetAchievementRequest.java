package application.request;

import domain.amount.TargetAmount;
import domain.amount.TargetAmountReachable;
import domain.interest_rate.InterestRate;
import domain.tax.Taxable;

public record TargetAchievementRequest(TargetAmount targetAmount, TargetAmountReachable monthlyInvestmentAmount,
									   InterestRate interestRate, Taxable taxable) {

}
