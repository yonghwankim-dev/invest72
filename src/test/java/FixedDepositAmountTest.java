import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FixedDepositAmountTest {

	@Test
	void created(){
		LumpSumInvestmentAmount investmentAmount = new FixedDepositAmount(1_000_000);
		assertNotNull(investmentAmount);
	}

}
