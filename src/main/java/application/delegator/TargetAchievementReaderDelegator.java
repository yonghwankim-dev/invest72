package application.delegator;

import java.io.IOException;

import application.request.TargetAchievementRequest;
import domain.tax.NonTax;
import domain.tax.Taxable;

public class TargetAchievementReaderDelegator implements InvestmentReaderDelegator<TargetAchievementRequest> {

	@Override
	public TargetAchievementRequest readInvestmentRequest() throws IOException {
		int targetAmount = 10_000_000;
		int monthlyInvestmentAmount = 1_000_000;
		double interestRate = 0.05;
		Taxable taxable = new NonTax();
		return new TargetAchievementRequest(
			targetAmount,
			monthlyInvestmentAmount,
			interestRate,
			taxable
		);
	}
}
