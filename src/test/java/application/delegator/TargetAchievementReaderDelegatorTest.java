package application.delegator;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.builder.DefaultInvestmentRequestBuilder;
import application.builder.InvestmentRequestBuilder;
import application.request.TargetAchievementRequest;

class TargetAchievementReaderDelegatorTest {

	private InvestmentReaderDelegator<TargetAchievementRequest> delegator;

	@BeforeEach
	void setUp() {
		InvestmentRequestBuilder investmentRequestBuilder = new DefaultInvestmentRequestBuilder();
		delegator = new TargetAchievementReaderDelegator(investmentRequestBuilder);
	}

	@Test
	void readInvestmentRequest() throws IOException {
		TargetAchievementRequest request = delegator.readInvestmentRequest();

		Assertions.assertNotNull(request);
		Assertions.assertEquals(0, request.initialCapital());
		Assertions.assertEquals(10_000_000, request.targetAmount());
		Assertions.assertEquals(1_000_000, request.monthlyInvestmentAmount());
		Assertions.assertEquals(0.05, request.interestRate());
		Assertions.assertEquals("비과세", request.taxType());
		Assertions.assertEquals(0.0, request.taxRate());
	}
}
