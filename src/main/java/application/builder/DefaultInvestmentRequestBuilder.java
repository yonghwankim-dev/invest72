package application.builder;

import application.request.CalculateInvestmentRequest;
import application.request.TargetAchievementRequest;

public class DefaultInvestmentRequestBuilder implements InvestmentRequestBuilder {
	@Override
	public CalculateInvestmentRequest.CalculateInvestmentRequestBuilder calculateInvestmentRequestBuilder() {
		return CalculateInvestmentRequest.builder();
	}

	@Override
	public TargetAchievementRequest.TargetAchievementRequestBuilder targetAchievementRequestBuilder() {
		return TargetAchievementRequest.builder();
	}
}
