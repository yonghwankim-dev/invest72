package application.factory;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import domain.investment.MonthlyInvestment;

class MonthlyInvestmentFactoryTest {

	@Test
	void created() {
		InvestmentFactory<MonthlyInvestment> factory = new MonthlyInvestmentFactory();
		assertNotNull(factory);
	}

}
