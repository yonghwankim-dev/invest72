import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BalanceTest {
	@Test
	void created(){
		Balance balance = new CompoundBalance();
		Assertions.assertNotNull(balance);
	}
}
