package application.delegator;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import application.request.TargetAchievementRequest;

class TargetAchievementReaderDelegatorTest {

	@Test
	void created() {
		InvestmentReaderDelegator<TargetAchievementRequest> delegator = new TargetAchievementReaderDelegator();

		Assertions.assertNotNull(delegator);
	}

	@Test
	void readInvestmentRequest() throws IOException {
		InvestmentReaderDelegator<TargetAchievementRequest> delegator = new TargetAchievementReaderDelegator();

		TargetAchievementRequest request = delegator.readInvestmentRequest();

		Assertions.assertNotNull(request);
		Assertions.assertEquals(0, request.initialCapital());
		Assertions.assertEquals(10_000_000, request.targetAmount());
		Assertions.assertEquals(1_000_000, request.monthlyInvestmentAmount());
	}
}
