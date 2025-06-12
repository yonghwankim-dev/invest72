import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AnnualInterestTest {
	@Test
	void created(){
		Interest interest = new AnnualInterest();
		Assertions.assertNotNull(interest);
	}
}
