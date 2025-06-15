import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FixedDepositTest {
	@Test
	void created(){
		Investment investment = new FixedDeposit();
		assertNotNull(investment);
	}
}
