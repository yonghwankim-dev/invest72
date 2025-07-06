package application.usecase;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.request.CalculateInvestmentRequest;
import application.response.CalculateInvestmentResponse;

class MonthlyCalculateInvestmentUseCaseTest {

	private CalculateInvestmentRequest request;

	@BeforeEach
	void setUp() {
		request = new CalculateInvestmentRequest(
			"적금",
			"월 1000000",
			"년",
			1,
			"복리",
			0.05,
			"일반과세",
			0.154
		);
	}

	@Test
	void created() {
		InvestmentUseCase useCase = new MonthlyCalculateInvestmentUseCase();
		Assertions.assertNotNull(useCase);
	}

	@Test
	void calInvestmentAmount() {
		InvestmentUseCase useCase = new MonthlyCalculateInvestmentUseCase();

		CalculateInvestmentResponse response = useCase.calInvestmentAmount(request);

		Assertions.assertNotNull(response);
	}
}
