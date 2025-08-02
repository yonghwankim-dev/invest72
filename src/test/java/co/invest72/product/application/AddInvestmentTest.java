package co.invest72.product.application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import co.invest72.investment.domain.interest.InterestType;
import co.invest72.investment.domain.investment.InvestmentType;
import co.invest72.investment.domain.tax.TaxType;
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
		InvestmentProductEntity product = InvestmentProductEntity.builder()
			.uid("test-uid")
			.investmentType(InvestmentType.FIXED_DEPOSIT)
			.investmentAmount(1_000_000)
			.interestType(InterestType.SIMPLE)
			.annualRate(0.05)
			.investmentPeriodMonth(12)
			.taxType(TaxType.STANDARD)
			.taxRate(0.154)
			.build();

		Long id = addInvestmentProduct.save(product);

		Assertions.assertTrue(id > 0);
		Assertions.assertEquals(product, repository.findById(id).orElseThrow());
	}
}
