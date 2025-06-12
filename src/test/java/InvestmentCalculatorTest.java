import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class InvestmentCalculatorTest {

	@Test
	void created(){
		InvestmentCalculator calculator = new CompoundMonthlyInvestmentCalculator();
		Assertions.assertNotNull(calculator);
	}

	@Test
	void shouldReturnPrincipal(){
		InvestmentCalculator calculator = new CompoundMonthlyInvestmentCalculator();
		// 월 투자 금액(원)
		int monthlyInvestment = 1_000_000;
		int principal = calculator.calculate(monthlyInvestment);
		int expected = 1_000_000;
		Assertions.assertEquals(expected, principal);
	}
}
