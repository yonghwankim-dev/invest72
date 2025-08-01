package co.invest72.investment.application;

import co.invest72.investment.domain.Investment;

public class AddInvestment {

	private final InvestmentProductRepository repository;

	public AddInvestment(InvestmentProductRepository repository) {
		this.repository = repository;
	}

	public Long save(String uid, Investment investment) {
		InvestmentProduct product = repository.save(uid, investment);
		return product.getId();
	}
}
