package application.usecase;

import application.request.CalculateInvestmentRequest;

public interface InvestmentUseCase {

	int calAmount(CalculateInvestmentRequest request);
}
