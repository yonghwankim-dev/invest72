package co.invest72.investment.application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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

	private InvestmentProductRepository repository;
	private AddInvestment addInvestment;

	@BeforeEach
	void setUp() {
		repository = new InvestmentProductInMemoryRepository();
		addInvestment = new AddInvestment(repository);
	}

	@Test
	void shouldSaveProduct() {
		String uid = "test-uid";
		Investment investment = createSimpleFixedDeposit();
		InvestmentProduct product = new InvestmentProduct(uid, investment);

		Long id = addInvestment.save(product);

		Assertions.assertTrue(id > 0);
		Assertions.assertEquals(product, repository.findById(id).orElseThrow());
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
