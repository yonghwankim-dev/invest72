package application.delegator;

import java.io.IOException;

import application.request.TargetAchievementRequest;

public class TargetAchievementReaderDelegator implements InvestmentReaderDelegator<TargetAchievementRequest> {

	@Override
	public TargetAchievementRequest readInvestmentRequest() throws IOException {
		return null;
	}
}
