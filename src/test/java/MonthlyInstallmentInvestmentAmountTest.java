import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MonthlyInstallmentInvestmentAmountTest {

	@Test
	void created(){
		InvestmentAmount investmentAmount = new MonthlyInstallmentInvestmentAmount(1_000_000);
		assertNotNull(investmentAmount);
	}

}
