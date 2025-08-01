package co.invest72.investment.application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import co.invest72.investment.domain.Investment;
import co.invest72.investment.domain.Taxable;
import co.invest72.investment.domain.TaxableFactory;
import co.invest72.investment.domain.amount.FixedDepositAmount;
import co.invest72.investment.domain.interest.AnnualInterestRate;
import co.invest72.investment.domain.investment.SimpleFixedDeposit;
import co.invest72.investment.domain.period.MonthlyInvestPeriod;
import co.invest72.investment.domain.tax.FixedTaxRate;
import co.invest72.investment.domain.tax.KoreanTaxableFactory;

class AddInvestmentTest {

	@Test
	void canCreated() {
		AddInvestment addInvestment = new AddInvestment();
		Assertions.assertNotNull(addInvestment);
	}

	@Test
	void shouldSaveProduct() {
		AddInvestment addInvestment = new AddInvestment();
		String uid = "test-uid";
		Investment investment = createSimpleFixedDeposit();

		addInvestment.save(uid, investment);
	}

	private Investment createSimpleFixedDeposit() {
		TaxableFactory taxableFactory = new KoreanTaxableFactory();
		Taxable taxable = taxableFactory.createStandardTax(new FixedTaxRate(0.154));
		return new SimpleFixedDeposit(
			new FixedDepositAmount(1000000),
			new MonthlyInvestPeriod(12),
			new AnnualInterestRate(0.05),
			taxable
		);
	}
}
