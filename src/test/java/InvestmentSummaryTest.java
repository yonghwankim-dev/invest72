import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class InvestmentSummaryTest {
	@Test
	void created(){
		InvestmentSummary summary = new CompoundMonthlyInvestmentSummary();
		Assertions.assertNotNull(summary);
	}
}
