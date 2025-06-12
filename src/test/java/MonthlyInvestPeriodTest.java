import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MonthlyInvestPeriodTest {

	private InvestPeriod investPeriod;

	@BeforeEach
	void setUp() {
		investPeriod = new MonthlyInvestPeriod(12);
	}

	@Test
	void created(){
		Assertions.assertNotNull(investPeriod);
	}

	@Test
	void shouldCreated_whenMonthsIsZero(){
		investPeriod = new MonthlyInvestPeriod(0);
		Assertions.assertNotNull(investPeriod);
	}

	@Test
	void shouldReturnMonths() {
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

	@Test
	void shouldReturnTotalPrincipal_givenMonthlyInvestment() {
		investPeriod = new MonthlyInvestPeriod(12);
		int monthlyInvestment = 1_000_000; // 월 투자 금액(원)

		int totalPrincipal = investPeriod.getTotalPrincipal(monthlyInvestment);

		int expected = 12_000_000;
		Assertions.assertEquals(expected, totalPrincipal);
	}
}
