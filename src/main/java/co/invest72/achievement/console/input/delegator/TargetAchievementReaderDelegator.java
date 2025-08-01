package co.invest72.achievement.console.input.delegator;

import java.io.IOException;

import co.invest72.achievement.application.CalculateAchievement;
import co.invest72.achievement.console.input.reader.TargetAchievementRequestReader;

public class TargetAchievementReaderDelegator {

	private final TargetAchievementRequestReader reader;

	public TargetAchievementReaderDelegator(TargetAchievementRequestReader reader) {
		this.reader = reader;
	}

	public CalculateAchievement.AchievementRequest readRequest() throws IOException {
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
