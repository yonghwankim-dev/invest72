package co.invest72.investment.application;

import co.invest72.investment.domain.Investment;

public class InvestmentProduct {
	private Long id;
	private String uid;
	private Investment investment;

	public InvestmentProduct(Long id, String uid, Investment investment) {
		this.id = id;
		this.uid = uid;
		this.investment = investment;
	}

	public Long getId() {
		return id;
	}

	public String getUid() {
		return uid;
	}

	public Investment getInvestment() {
		return investment;
	}
}
