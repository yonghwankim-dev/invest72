package co.invest72.product.application;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import co.invest72.investment.domain.interest.InterestType;
import co.invest72.investment.domain.investment.InvestmentType;
import co.invest72.investment.domain.tax.TaxType;
import co.invest72.product.domain.InvestmentProductEntity;
import co.invest72.product.domain.InvestmentProductRepository;
import co.invest72.product.persistence.InvestmentProductInMemoryRepository;

class GetInvestmentProductsTest {

	private InvestmentProductEntity createSimpleFixedDepositInvestmentProductEntity(String uid) {
		return InvestmentProductEntity.builder()
			.uid(uid)
			.investmentType(InvestmentType.FIXED_DEPOSIT)
			.investmentAmount(1_000_000)
			.interestType(InterestType.SIMPLE)
			.annualRate(0.05)
			.investmentPeriodMonth(12)
			.taxType(TaxType.STANDARD)
			.taxRate(0.154)
			.build();
	}

	@Test
	void listInvestment() {
		InvestmentProductRepository repository = new InvestmentProductInMemoryRepository();
		String uid = "test-uid";
		InvestmentProductEntity investmentProductEntity = createSimpleFixedDepositInvestmentProductEntity(uid);
		repository.save(investmentProductEntity);

		GetInvestmentProducts getInvestmentProducts = new GetInvestmentProducts(repository);

		List<InvestmentProductDto> result = getInvestmentProducts.listInvestment(uid);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(1, result.size());
	}

	@Test
	void shouldReturnEmptyList_whenUidIsNull() {
		InvestmentProductRepository repository = new InvestmentProductInMemoryRepository();
		String uid = null;
		InvestmentProductEntity investmentProductEntity = createSimpleFixedDepositInvestmentProductEntity(uid);
		repository.save(investmentProductEntity);

		GetInvestmentProducts getInvestmentProducts = new GetInvestmentProducts(repository);

		List<InvestmentProductDto> result = getInvestmentProducts.listInvestment(uid);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(0, result.size());
	}
}
