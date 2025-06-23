import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class YearlyInstallmentInvestmentAmountTest {

	private InstallmentInvestmentAmount investmentAmount;

	@BeforeEach
	void setUp() {
		investmentAmount = new YearlyInstallmentInvestmentAmount(12_000_000);
	}

	@Test
	void created(){
		assertNotNull(investmentAmount);
	}

	@Test
	void shouldReturnAmount(){
		int amount = investmentAmount.getMonthlyAmount();

		int expectedAmount = 1_000_000;
		assertEquals(expectedAmount, amount);
	}
}
