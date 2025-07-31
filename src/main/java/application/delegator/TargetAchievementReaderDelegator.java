package application.delegator;

import java.io.IOException;

import application.reader.TargetAchievementRequestReader;
import application.request.TargetAchievementRequest;

public class TargetAchievementReaderDelegator implements InvestmentReaderDelegator<TargetAchievementRequest> {

	private final TargetAchievementRequestReader reader;

	public TargetAchievementReaderDelegator(TargetAchievementRequestReader reader) {
		this.reader = reader;
	}

	@Override
	public TargetAchievementRequest readInvestmentRequest() throws IOException {
		int targetAmount = reader.readTargetAmount();
		int monthlyInvestmentAmount = Integer.parseInt(reader.readAmount());
		double interestRate = reader.readInterestRate();
		String taxType = reader.readTaxType();
		double taxRate = reader.readTaxRate();

		return TargetAchievementRequest.builder()
			.targetAmount(targetAmount)
			.monthlyInvestmentAmount(monthlyInvestmentAmount)
			.interestRate(interestRate)
			.taxType(taxType)
			.taxRate(taxRate)
			.build();
	}
}
