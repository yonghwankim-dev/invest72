package co.invest72.investment.application;

import co.invest72.investment.domain.Investment;

public class AddInvestment {
	public void save(String uid, Investment investment) {
		InvestmentProductRepository repository = new InvestmentProductInMemoryRepository();
		repository.save(uid, investment);
	}
}
