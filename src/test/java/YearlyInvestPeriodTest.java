import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class YearlyInvestPeriodTest {

	@Test
	void created(){
		InvestPeriod investPeriod = new YearlyInvestPeriod(10);
		assertNotNull(investPeriod);
	}

	@Test
	void shouldReturnMonths() {
		InvestPeriod investPeriod = new YearlyInvestPeriod(10);

		int months = investPeriod.getMonths();

		int expectedMonths = 120;
		assertEquals(expectedMonths, months);
	}
}
