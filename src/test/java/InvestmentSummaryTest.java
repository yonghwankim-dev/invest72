import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InvestmentSummaryTest {

	private int principal;

	@BeforeEach
	void setUp() {
		principal = 12_000_000;
	}

	@Test
	void created(){
		int interest = 0;
		InvestmentSummary summary = new CompoundMonthlyInvestmentSummary(principal, interest);
		Assertions.assertNotNull(summary);
	}

	@Test
	void getPrincipal_shouldReturnPrincipal(){
		int interest = 0;
		InvestmentSummary summary = new CompoundMonthlyInvestmentSummary(principal, interest);

		int actual = summary.getPrincipal();

		int expected = 12_000_000;
		Assertions.assertEquals(expected, actual);
	}
}
