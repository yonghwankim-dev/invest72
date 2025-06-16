import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FixedDepositTest {

	private Investment investment;

	@BeforeEach
	void setUp() {
		LumpSumInvestmentAmount investmentAmount = new FixedDepositAmount(1_000_000);
		TaxableFactory taxableFactory = new KoreanTaxableFactory();
		Taxable taxable = taxableFactory.createNonTax();
		investment = new FixedDeposit(investmentAmount, taxable);
	}

	@Test
	void created(){
		assertNotNull(investment);
	}

	// todo: 이 테스트는 FixedDeposit의 getAmount() 메서드가 올바른 금액을 반환하는지 확인합니다.
	@Test
	void shouldReturnAmount(){
		int amount = investment.getAmount();

		int expectedAmount = 1_051_162;
		assertEquals(expectedAmount, amount);
	}
}
