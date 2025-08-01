package co.invest72.investment.application;

import java.util.Objects;

import co.invest72.investment.domain.Investment;

public class InvestmentProduct {
	private Long id;
	private String uid;
	private Investment investment;

	public InvestmentProduct(String uid, Investment investment) {
		this.uid = uid;
		this.investment = investment;
	}

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

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;
		InvestmentProduct product = (InvestmentProduct)object;
		return Objects.equals(id, product.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
