package co.invest72.investment.application;

import co.invest72.investment.domain.Investment;

public interface InvestmentProductRepository {
	InvestmentProduct save(String uid, Investment investment);
}
