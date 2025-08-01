package co.invest72.investment.domain.tax;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import co.invest72.investment.domain.Taxable;
import co.invest72.investment.domain.tax.FixedTaxRate;
import co.invest72.investment.domain.tax.TaxBenefit;

class TaxBenefitTest {
	@Test
	void created() {
		Taxable taxable = new TaxBenefit(new FixedTaxRate(0.014));
		assertNotNull(taxable);
	}
}
