package co.invest72.product.application;

import co.invest72.product.domain.InvestmentProduct;
import co.invest72.product.domain.InvestmentProductRepository;

public class AddInvestmentProduct {

	private final InvestmentProductRepository repository;

	public AddInvestmentProduct(InvestmentProductRepository repository) {
		this.repository = repository;
	}

	public Long save(InvestmentProduct product) {
		InvestmentProduct saveProduct = repository.save(product);
		return saveProduct.getId();
	}
}
