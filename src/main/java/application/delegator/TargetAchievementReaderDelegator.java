package application.delegator;

import java.io.IOException;

import application.builder.InvestmentRequestBuilder;
import application.reader.TargetAchievementRequestReader;
import application.request.TargetAchievementRequest;

public class TargetAchievementReaderDelegator implements InvestmentReaderDelegator<TargetAchievementRequest> {

	private final InvestmentRequestBuilder requestBuilder;
	private final TargetAchievementRequestReader reader;

	public TargetAchievementReaderDelegator(InvestmentRequestBuilder requestBuilder,
		TargetAchievementRequestReader reader) {
		this.requestBuilder = requestBuilder;
		this.reader = reader;
	}

	@Override
	public TargetAchievementRequest readInvestmentRequest() throws IOException {
		int targetAmount = reader.readTargetAmount();
		int monthlyInvestmentAmount = Integer.parseInt(reader.readAmount());
		double interestRate = reader.readInterestRate();
		String taxType = reader.readTaxType();
		double taxRate = reader.readTaxRate();

		return requestBuilder.targetAchievementRequestBuilder()
			.targetAmount(targetAmount)
			.monthlyInvestmentAmount(monthlyInvestmentAmount)
			.interestRate(interestRate)
			.taxType(taxType)
			.taxRate(taxRate)
			.build();
	}
}
