package domain.tax.factory;

import domain.tax.StandardTax;
import domain.tax.TaxBenefit;
import domain.tax.TaxRate;
import domain.tax.Taxable;
import domain.type.TaxType;

public class KoreanTaxableFactory implements TaxableFactory {
	@Override
	public Taxable createStandardTax(TaxRate taxRate) {
		return new StandardTax(taxRate);
	}

	@Override
	public Taxable createTaxBenefit(TaxRate taxRate) {
		return new TaxBenefit(taxRate);
	}

	@Override
	public Taxable createBy(TaxType taxType, TaxRate taxRate) {
		if (taxType == TaxType.STANDARD) {
			return createStandardTax(taxRate);
		} else if (taxType == TaxType.TAX_BENEFIT) {
			return createTaxBenefit(taxRate);
		} else if (taxType == TaxType.NON_TAX) {
			return createNonTax();
		}
		throw new IllegalArgumentException("Unknown tax type: " + taxType);
	}
}
