import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class InvestmentCalculatorTest {

	@Test
	void created(){
		InvestmentCalculator calculator = new CompoundMonthlyInvestmentCalculator();
		Assertions.assertNotNull(calculator);
	}
}
