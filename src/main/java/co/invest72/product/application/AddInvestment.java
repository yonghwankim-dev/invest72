package co.invest72.product.application;

import co.invest72.investment.domain.Investment;
import co.invest72.product.domain.InvestmentProduct;
import co.invest72.product.domain.InvestmentProductRepository;

public class AddInvestment {

	private final InvestmentProductRepository repository;

	public AddInvestment(InvestmentProductRepository repository) {
		this.repository = repository;
	}

	public Long save(InvestmentProduct product) {
		InvestmentProduct saveProduct = repository.save(product);
		return saveProduct.getId();
	}

	public static record AddInvestmentResponse(Long id, String uid, Investment investment) {

	}
}
