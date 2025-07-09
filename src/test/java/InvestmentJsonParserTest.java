import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import application.builder.DefaultInvestmentRequestBuilder;
import application.builder.InvestmentRequestBuilder;
import application.request.CalculateInvestmentRequest;

class InvestmentJsonParserTest {

	@Test
	void parse_shouldReturnInvestmentRequest() {
		InvestmentRequestBuilder builder = new DefaultInvestmentRequestBuilder();
		CalculateInvestmentRequest expectedInvestmentRequest = builder.calculateInvestmentRequestBuilder()
			.type("예금")
			.amount("1000000")
			.periodType("년")
			.periodValue(1)
			.interestType("단리")
			.interestRate(0.05)
			.taxType("일반과세")
			.taxRate(0.154)
			.build();

		CalculateInvestmentRequest request = parse("src/test/resources/test_input1.json");
		Assertions.assertEquals(expectedInvestmentRequest, request);
	}

	private CalculateInvestmentRequest parse(String path) {
		File file = new File(path);
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.readValue(file, CalculateInvestmentRequest.class);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
