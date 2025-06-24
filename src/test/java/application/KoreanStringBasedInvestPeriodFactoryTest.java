package application;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.invest_period.InvestPeriod;

class KoreanStringBasedInvestPeriodFactoryTest {

	private InvestPeriodFactory investPeriodFactory;

	@BeforeEach
	void setUp() {
		investPeriodFactory = new KoreanStringBasedInvestPeriodFactory();
	}

	@Test
	void created() {
		assertNotNull(investPeriodFactory);
	}

	@Test
	void shouldReturnInvestPeriod_whenStringIsMonth() {
		InvestPeriod investPeriod = investPeriodFactory.createBy("월", 12);

		assertInstanceOf(domain.invest_period.MonthlyInvestPeriod.class, investPeriod);
	}

	@Test
	void shouldReturnInvestPeriod_whenStringIsYear() {
		InvestPeriod investPeriod = investPeriodFactory.createBy("년", 1);

		assertInstanceOf(domain.invest_period.YearlyInvestPeriod.class, investPeriod);
	}
}
