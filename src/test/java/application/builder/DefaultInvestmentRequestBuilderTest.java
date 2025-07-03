package application.builder;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import application.request.CalculateInvestmentRequest;

class DefaultInvestmentRequestBuilderTest {

	@Test
	void created() {
		DefaultInvestmentRequestBuilder builder = new DefaultInvestmentRequestBuilder();
		assertNotNull(builder);
	}

	@Test
	void calculateInvestmentRequestBuilderCreated() {
		DefaultInvestmentRequestBuilder builder = new DefaultInvestmentRequestBuilder();

		CalculateInvestmentRequest.CalculateInvestmentRequestBuilder requestBuilder = builder.calculateInvestmentRequestBuilder();

		assertNotNull(requestBuilder);
	}
}
