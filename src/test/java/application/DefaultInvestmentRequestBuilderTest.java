package application;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DefaultInvestmentRequestBuilderTest {

	@Test
	void created() {
		DefaultInvestmentRequestBuilder builder = new DefaultInvestmentRequestBuilder();
		assertNotNull(builder);
	}
}
