package application.delegator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import adapter.console.ui.BufferedWriterBasedGuidePrinter;
import adapter.ui.GuidePrinter;
import application.builder.DefaultInvestmentRequestBuilder;
import application.builder.InvestmentRequestBuilder;
import application.reader.TargetAchievementRequestReader;
import application.request.TargetAchievementRequest;

class TargetAchievementReaderDelegatorTest {

	private InvestmentReaderDelegator<TargetAchievementRequest> delegator;

	@BeforeEach
	void setUp() {
		InvestmentRequestBuilder investmentRequestBuilder = new DefaultInvestmentRequestBuilder();
		String input = String.join(System.lineSeparator(),
			"10000000", // targetAmount
			"1000000", // monthlyInvestmentAmount
			"5", // interestRate
			"비과세", // taxType
			"0.0" // taxRate
		);
		InputStream in = new ByteArrayInputStream(input.getBytes());
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
		GuidePrinter guidePrinter = new BufferedWriterBasedGuidePrinter(
			new BufferedWriter(new OutputStreamWriter(System.out)));
		TargetAchievementRequestReader reader = new TargetAchievementRequestReader(bufferedReader, guidePrinter);
		delegator = new TargetAchievementReaderDelegator(investmentRequestBuilder, reader);
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
