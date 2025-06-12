import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class InvestmentSummaryTest {
	@Test
	void created(){
		int principal = 12_000_000;
		InvestmentSummary summary = new CompoundMonthlyInvestmentSummary(principal);
		Assertions.assertNotNull(summary);
	}

	@Test
	void getPrincipal_shouldReturnPrincipal(){
		int principal = 12_000_000;
		InvestmentSummary summary = new CompoundMonthlyInvestmentSummary(principal);

		int actual = summary.getPrincipal();

		int expected = 12_000_000;
		Assertions.assertEquals(expected, actual);
	}
}
