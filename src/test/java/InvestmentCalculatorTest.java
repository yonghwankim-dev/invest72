import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InvestmentCalculatorTest {

	private InvestmentCalculator calculator;

	@BeforeEach
	void setUp() {
		calculator = new CompoundMonthlyInvestmentCalculator();
	}

	@Test
	void created(){
		Assertions.assertNotNull(calculator);
	}

	@Test
	void shouldReturnPrincipal(){
		// 월 투자 금액(원)
		int monthlyInvestment = 1_000_000;
		int principal = calculator.calculate(monthlyInvestment);
		int expected = 1_000_000;
		Assertions.assertEquals(expected, principal);
	}
}
