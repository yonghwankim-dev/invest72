package application.delegator;

import java.io.IOException;

import application.reader.TargetAchievementRequestReader;
import co.invest72.achievement.application.CalculateAchievement;

public class TargetAchievementReaderDelegator
	implements InvestmentReaderDelegator<CalculateAchievement.AchievementRequest> {

	private final TargetAchievementRequestReader reader;

	public TargetAchievementReaderDelegator(TargetAchievementRequestReader reader) {
		this.reader = reader;
	}

	@Override
	public CalculateAchievement.AchievementRequest readInvestmentRequest() throws IOException {
		int targetAmount = reader.readTargetAmount();
		int monthlyInvestmentAmount = Integer.parseInt(reader.readAmount());
		double interestRate = reader.readInterestRate();
		String taxType = reader.readTaxType();
		double taxRate = reader.readTaxRate();

		return CalculateAchievement.AchievementRequest.builder()
			.targetAmount(targetAmount)
			.monthlyInvestmentAmount(monthlyInvestmentAmount)
			.interestRate(interestRate)
			.taxType(taxType)
			.taxRate(taxRate)
			.build();
	}
}
