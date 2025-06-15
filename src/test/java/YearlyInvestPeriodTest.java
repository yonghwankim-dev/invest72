import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class YearlyInvestPeriodTest {

	@Test
	void created(){
		InvestPeriod investPeriod = new YearlyInvestPeriod(10);
		assertNotNull(investPeriod);
	}
}
