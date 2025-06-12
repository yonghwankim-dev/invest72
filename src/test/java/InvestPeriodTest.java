import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class InvestPeriodTest {

	@Test
	void created(){
		InvestPeriod investPeriod = new MonthlyInvestPeriod(12);
		Assertions.assertNotNull(investPeriod);
	}

	@Test
	void shouldReturnBalance() {
		InvestPeriod investPeriod = new MonthlyInvestPeriod(12);

		int actualMonths = investPeriod.getMonths();

		int expectedMonths = 12;
		Assertions.assertEquals(expectedMonths, actualMonths);
	}

	@Test
	void shouldThrowException_whenInvestmentPeriodIsNegative(){
		Assertions.assertThrows(IllegalArgumentException.class,
			() -> new MonthlyInvestPeriod(-1));
	}


	@Test
	void shouldThrowException_whenInvestmentPeriodGreaterThan999(){
		Assertions.assertThrows(IllegalArgumentException.class,
			() -> new MonthlyInvestPeriod(1000));
	}
}
