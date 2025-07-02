package application;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

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
