import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class InvestPeriodTest {

	@Test
	void created(){
		InvestPeriod investPeriod = new MonthlyInvestPeriod(12);
		Assertions.assertNotNull(investPeriod);
	}
}
