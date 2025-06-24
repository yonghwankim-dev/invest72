package domain.tax;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TaxBenefitTest {
	@Test
	void created() {
		Taxable taxable = new TaxBenefit(new FixedTaxRate(0.014));
		assertNotNull(taxable);
	}
}
