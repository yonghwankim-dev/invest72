package application.factory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import application.request.CalculateInvestmentRequest;
import domain.investment.CompoundFixedInstallmentSaving;
import domain.investment.MonthlyInvestment;

class MonthlyInvestmentFactoryTest {
	@Test
	void created() {
		InvestmentFactory<MonthlyInvestment> factory = new MonthlyInvestmentFactory();

		Assertions.assertNotNull(factory);
	}

	@Test
	void createBy_whenCompoundFixedInstallmentSaving() {
		InvestmentFactory<MonthlyInvestment> factory = new MonthlyInvestmentFactory();
		CalculateInvestmentRequest request = new CalculateInvestmentRequest(
			"적금",
			"월 1000000",
			"년",
			1,
			"복리",
			0.05,
			"비과세",
			0.0
		);

		MonthlyInvestment monthlyInvestment = factory.createBy(request);

		Assertions.assertInstanceOf(CompoundFixedInstallmentSaving.class, monthlyInvestment);
	}
}
