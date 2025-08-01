package co.invest72.investment.application;

import co.invest72.investment.domain.Investment;

public class AddInvestment {
	public AddInvestmentResponse save(String uid, Investment investment) {
		Long id = 1L;
		return new AddInvestmentResponse(id, uid, investment);
	}
}
