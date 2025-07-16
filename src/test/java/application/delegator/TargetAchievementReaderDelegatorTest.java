package application.delegator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import application.request.TargetAchievementRequest;

class TargetAchievementReaderDelegatorTest {

	@Test
	void created() {
		InvestmentReaderDelegator<TargetAchievementRequest> delegator = new TargetAchievementReaderDelegator();

		Assertions.assertNotNull(delegator);
	}
}
