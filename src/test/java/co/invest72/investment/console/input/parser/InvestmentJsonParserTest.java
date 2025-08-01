package co.invest72.investment.console.input.parser;

import java.io.File;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.invest72.investment.application.dto.CalculateInvestmentRequest;

class InvestmentJsonParserTest {

	private CalculateInvestmentRequestParser parser;

	@BeforeEach
	void setUp() {
		parser = new JsonCalculateInvestmentRequestParser(new ObjectMapper());
	}

	@Test
	void parse_shouldReturnInvestmentRequest() {
		CalculateInvestmentRequest expectedInvestmentRequest = CalculateInvestmentRequest.builder()
			.type("예금")
			.amount("1000000")
			.periodType("년")
			.periodValue(1)
			.interestType("단리")
			.interestRate(0.05)
			.taxType("일반과세")
			.taxRate(0.154)
			.build();
		File file = new File("src/test/resources/simple-fixed-deposit.json");

		CalculateInvestmentRequest request = parser.parse(file);
		Assertions.assertEquals(expectedInvestmentRequest, request);
	}

	@Test
	void parse_shouldThrowException_whenPeriodValueIsStringText() {
		File file = new File("src/test/resources/invalid_input.json");
		Assertions.assertThrows(IllegalArgumentException.class, () -> parser.parse(file));
	}
}
