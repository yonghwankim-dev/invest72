package co.invest72.product.application;

import co.invest72.investment.domain.Investment;
import co.invest72.product.domain.InvestmentProduct;

public class InvestmentProductDto {
	private final Long id;
	private final String uid;
	private final Investment investment;

	public InvestmentProductDto(Long id, String uid, Investment investment) {
		this.id = id;
		this.uid = uid;
		this.investment = investment;
	}

	public static InvestmentProductDto from(InvestmentProduct investmentProduct) {
		return new InvestmentProductDto(
			investmentProduct.getId(),
			investmentProduct.getUid(),
			investmentProduct.getInvestment()
		);
	}
}
