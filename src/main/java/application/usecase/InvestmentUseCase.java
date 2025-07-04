package application.usecase;

import application.request.CalculateInvestmentRequest;
import application.response.CalculateInvestmentResponse;

public interface InvestmentUseCase {

	int calAmount(CalculateInvestmentRequest request);

	CalculateInvestmentResponse calInvestmentAmount(CalculateInvestmentRequest request);
}
