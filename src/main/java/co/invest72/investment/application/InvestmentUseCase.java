package co.invest72.investment.application;

import application.request.CalculateInvestmentRequest;
import application.response.CalculateInvestmentResponse;
import application.response.CalculateMonthlyInvestmentResponse;

public interface InvestmentUseCase {
	CalculateInvestmentResponse calInvestmentAmount(CalculateInvestmentRequest request);

	CalculateMonthlyInvestmentResponse calMonthlyInvestmentAmount(CalculateInvestmentRequest request);
}
