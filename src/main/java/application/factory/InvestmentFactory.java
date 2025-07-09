package application.factory;

import application.request.CalculateInvestmentRequest;

public interface InvestmentFactory<R> {
	R createBy(CalculateInvestmentRequest request);
}
