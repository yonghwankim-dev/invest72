package application.factory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import application.request.CalculateInvestmentRequest;
import domain.investment.CompoundFixedDeposit;
import domain.investment.MonthlyInvestment;
import domain.investment.SimpleFixedDeposit;
import domain.investment.SimpleFixedInstallmentSaving;

class MonthlyInvestmentFactoryTest {
	@Test
	void created() {
		InvestmentFactory<MonthlyInvestment> factory = new MonthlyInvestmentFactory();

		Assertions.assertNotNull(factory);
	}

	@Test
	void createBy_whenSimpleFixedDeposit() {
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

	@Test
	void createBy_whenCompoundFixedDeposit() {
		InvestmentFactory<MonthlyInvestment> factory = new MonthlyInvestmentFactory();
		CalculateInvestmentRequest request = new CalculateInvestmentRequest(
			"예금",
			"1000000",
			"년",
			1,
			"복리",
			0.05,
			"비과세",
			0.0
		);

		MonthlyInvestment monthlyInvestment = factory.createBy(request);

		Assertions.assertInstanceOf(CompoundFixedDeposit.class, monthlyInvestment);
	}

	@Test
	void createBy_whenSimpleFixedInstallmentSaving() {
		InvestmentFactory<MonthlyInvestment> factory = new MonthlyInvestmentFactory();
		CalculateInvestmentRequest request = new CalculateInvestmentRequest(
			"적금",
			"월 1000000",
			"년",
			1,
			"단리",
			0.05,
			"비과세",
			0.0
		);

		MonthlyInvestment monthlyInvestment = factory.createBy(request);

		Assertions.assertInstanceOf(SimpleFixedInstallmentSaving.class, monthlyInvestment);
	}
}
