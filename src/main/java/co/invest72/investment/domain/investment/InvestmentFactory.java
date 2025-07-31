package co.invest72.investment.domain.investment;

import application.request.CalculateInvestmentRequest;

public interface InvestmentFactory<R> {
	R createBy(CalculateInvestmentRequest request);
}
