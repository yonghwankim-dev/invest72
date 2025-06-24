package application;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import domain.invest_period.InvestPeriod;

class KoreanStringBasedInvestPeriodFactoryTest {

	@Test
	void created() {
		InvestPeriodFactory investPeriodFactory = new KoreanStringBasedInvestPeriodFactory();

		assertNotNull(investPeriodFactory);
	}

	@Test
	void shouldReturnInvestPeriod_whenStringIsMonth() {
		InvestPeriodFactory investPeriodFactory = new KoreanStringBasedInvestPeriodFactory();

		InvestPeriod investPeriod = investPeriodFactory.createBy("월", 12);

		assertInstanceOf(domain.invest_period.MonthlyInvestPeriod.class, investPeriod);
	}
}
