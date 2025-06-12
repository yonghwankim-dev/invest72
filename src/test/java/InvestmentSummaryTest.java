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
		InvestmentSummary summary = new CompoundMonthlyInvestmentSummary(principal);
		Assertions.assertNotNull(summary);
	}

	@Test
	void getPrincipal_shouldReturnPrincipal(){
		InvestmentSummary summary = new CompoundMonthlyInvestmentSummary(principal);

		int actual = summary.getPrincipal();

		int expected = 12_000_000;
		Assertions.assertEquals(expected, actual);
	}
}
