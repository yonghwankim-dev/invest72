package co.invest72.product.application;

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
import co.invest72.product.domain.InvestmentProductEntity;
import co.invest72.product.domain.InvestmentProductRepository;
import co.invest72.product.persistence.InvestmentProductInMemoryRepository;

class AddInvestmentTest {

	private InvestmentProductRepository repository;
	private AddInvestmentProduct addInvestmentProduct;

	@BeforeEach
	void setUp() {
		repository = new InvestmentProductInMemoryRepository();
		addInvestmentProduct = new AddInvestmentProduct(repository);
	}

	@Test
	void shouldSaveProduct() {
		String uid = "test-uid";
		Investment investment = createSimpleFixedDeposit();
		InvestmentProductEntity product = new InvestmentProductEntity(uid, investment);

		Long id = addInvestmentProduct.save(product);

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
