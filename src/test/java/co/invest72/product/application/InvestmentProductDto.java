package co.invest72.product.application;

import co.invest72.investment.domain.interest.InterestType;
import co.invest72.investment.domain.investment.InvestmentType;
import co.invest72.investment.domain.tax.TaxType;
import co.invest72.product.domain.InvestmentProductEntity;

public record InvestmentProductDto(
	Long id,
	String uid,
	InvestmentType investmentType,
	int investmentAmount,
	InterestType interestType,
	double annualRate,
	int investmentPeriodMonth,
	TaxType taxType,
	double taxRate
) {

	public static InvestmentProductDto from(InvestmentProductEntity investmentProduct) {
		return new InvestmentProductDto(
			investmentProduct.getId(),
			investmentProduct.getUid(),
			investmentProduct.getInvestmentType(),
			investmentProduct.getInvestmentAmount(),
			investmentProduct.getInterestType(),
			investmentProduct.getAnnualRate(),
			investmentProduct.getInvestmentPeriodMonth(),
			investmentProduct.getTaxType(),
			investmentProduct.getTaxRate()
		);
	}
}
