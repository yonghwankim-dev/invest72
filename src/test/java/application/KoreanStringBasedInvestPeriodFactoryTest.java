package application;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.invest_period.InvestPeriod;
import domain.invest_period.PeriodMonthsRange;
import domain.invest_period.PeriodRange;
import domain.invest_period.PeriodYearRange;
import domain.type.PeriodType;

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
		PeriodRange periodRange = new PeriodMonthsRange(12);
		InvestPeriod investPeriod = investPeriodFactory.createBy(PeriodType.MONTH, periodRange);

		assertInstanceOf(domain.invest_period.MonthlyInvestPeriod.class, investPeriod);
	}

	@Test
	void shouldReturnInvestPeriod_whenStringIsYear() {
		PeriodRange periodRange = new PeriodYearRange(1);
		InvestPeriod investPeriod = investPeriodFactory.createBy(PeriodType.YEAR, periodRange);

		assertInstanceOf(domain.invest_period.YearlyInvestPeriod.class, investPeriod);
	}
}
