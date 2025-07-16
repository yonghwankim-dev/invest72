package application.delegator;

import java.io.IOException;

import application.request.TargetAchievementRequest;
import domain.interest_rate.AnnualInterestRate;
import domain.interest_rate.InterestRate;
import domain.tax.NonTax;
import domain.tax.Taxable;

public class TargetAchievementReaderDelegator implements InvestmentReaderDelegator<TargetAchievementRequest> {

	@Override
	public TargetAchievementRequest readInvestmentRequest() throws IOException {
		int targetAmount = 10_000_000;
		int monthlyInvestmentAmount = 1_000_000;
		InterestRate interestRate = new AnnualInterestRate(0.05);
		Taxable taxable = new NonTax();
		return new TargetAchievementRequest(
			targetAmount,
			monthlyInvestmentAmount,
			interestRate,
			taxable
		);
	}
}
