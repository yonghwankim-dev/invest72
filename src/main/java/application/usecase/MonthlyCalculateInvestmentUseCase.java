package application.usecase;

import application.request.CalculateInvestmentRequest;
import application.response.CalculateInvestmentResponse;

public class MonthlyCalculateInvestmentUseCase implements InvestmentUseCase {

	@Override
	public CalculateInvestmentResponse calInvestmentAmount(CalculateInvestmentRequest request) {
		return new CalculateInvestmentResponse(
			0, // 총 수익금액
			0, // 총 원금액
			0, // 이자
			0  // 세금
		);
	}
}
