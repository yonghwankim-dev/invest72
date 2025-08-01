package co.invest72.product.application;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import co.invest72.product.domain.InvestmentProductRepository;
import co.invest72.product.persistence.InvestmentProductInMemoryRepository;

class GetInvestmentProductsTest {

	@Test
	void listInvestment() {
		InvestmentProductRepository repository = new InvestmentProductInMemoryRepository();
		GetInvestmentProducts getInvestmentProducts = new GetInvestmentProducts(repository);
		String uid = "test-uid";

		List<InvestmentProductDto> result = getInvestmentProducts.listInvestment(uid);

		Assertions.assertNotNull(result);
	}
}
