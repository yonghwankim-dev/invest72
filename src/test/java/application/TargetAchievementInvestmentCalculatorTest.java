package application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import application.request.TargetAchievementRequest;
import domain.type.TaxType;

class TargetAchievementInvestmentCalculatorTest {

	@Test
	void calMonth() {
		InvestmentCalculator calculator = new TargetAchievementInvestmentCalculator();
		TargetAchievementRequest request = TargetAchievementRequest.builder()
			.targetAmount(10000000)
			.monthlyInvestmentAmount(1000000)
			.interestRate(0.05)
			.taxType(TaxType.STANDARD.getDescription())
			.taxRate(0.154)
			.build();
		int month = calculator.calMonth(request);

		Assertions.assertEquals(10, month);
	}

}
