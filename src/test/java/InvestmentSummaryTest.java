import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class InvestmentSummaryTest {
	@Test
	void created(){
		InvestmentSummary summary = new CompoundMonthlyInvestmentSummary();
		Assertions.assertNotNull(summary);
	}

	@Test
	void getPrincipal_shouldReturnPrincipal(){
		InvestmentSummary summary = new CompoundMonthlyInvestmentSummary();

		int principal = summary.getPrincipal();

		int expected = 0;
		Assertions.assertEquals(expected, principal);
	}
}
