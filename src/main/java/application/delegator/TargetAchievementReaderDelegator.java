package application.delegator;

import java.io.IOException;

import application.builder.InvestmentRequestBuilder;
import application.request.TargetAchievementRequest;
import domain.type.TaxType;

public class TargetAchievementReaderDelegator implements InvestmentReaderDelegator<TargetAchievementRequest> {

	private final InvestmentRequestBuilder requestBuilder;

	public TargetAchievementReaderDelegator(InvestmentRequestBuilder requestBuilder) {
		this.requestBuilder = requestBuilder;
	}

	@Override
	public TargetAchievementRequest readInvestmentRequest() throws IOException {
		int targetAmount = 10_000_000;
		int monthlyInvestmentAmount = 1_000_000;
		double interestRate = 0.05;
		String taxType = TaxType.NON_TAX.getDescription();
		double taxRate = 0.0;

		return requestBuilder.targetAchievementRequestBuilder()
			.targetAmount(targetAmount)
			.monthlyInvestmentAmount(monthlyInvestmentAmount)
			.interestRate(interestRate)
			.taxType(taxType)
			.taxRate(taxRate)
			.build();
	}
}
