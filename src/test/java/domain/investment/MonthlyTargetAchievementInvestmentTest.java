package domain.investment;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MonthlyTargetAchievementInvestmentTest {

	@Test
	void created() {
		TargetAchievementInvestment investment = new MonthlyTargetAchievementInvestment();
		assertNotNull(investment);
	}
}
