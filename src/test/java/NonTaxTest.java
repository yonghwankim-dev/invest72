import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class NonTaxTest {

	@Test
	void getAmount(){
		Taxable taxable = new NonTax();

		int amount = taxable.getAmount();

		int expectedAmount = 0;
		assertEquals(expectedAmount, amount, "The amount for NonTax should be 0.");
	}
}
