package co.invest72.investment.application;

import co.invest72.investment.domain.Investment;

public interface InvestmentProductRepository {
	void save(String uid, Investment investment);
}
