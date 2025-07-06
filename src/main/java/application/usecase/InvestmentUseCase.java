package application.usecase;

import application.request.CalculateInvestmentRequest;
import application.response.CalculateInvestmentResponse;

public interface InvestmentUseCase {
	CalculateInvestmentResponse calInvestmentAmount(CalculateInvestmentRequest request);
}
