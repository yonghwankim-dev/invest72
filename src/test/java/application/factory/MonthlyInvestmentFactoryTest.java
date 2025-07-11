package application.factory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import application.request.CalculateInvestmentRequest;
import domain.investment.MonthlyInvestment;
import domain.investment.SimpleFixedDeposit;

class MonthlyInvestmentFactoryTest {
	@Test
	void created() {
		InvestmentFactory<MonthlyInvestment> factory = new MonthlyInvestmentFactory();

		Assertions.assertNotNull(factory);
	}

	@Test
	void createBy() {
		InvestmentFactory<MonthlyInvestment> factory = new MonthlyInvestmentFactory();
		CalculateInvestmentRequest request = new CalculateInvestmentRequest(
			"예금",
			"1000000",
			"년",
			1,
			"단리",
			0.05,
			"비과세",
			0.0
		);

		MonthlyInvestment monthlyInvestment = factory.createBy(request);

		Assertions.assertInstanceOf(SimpleFixedDeposit.class, monthlyInvestment);
	}
}
