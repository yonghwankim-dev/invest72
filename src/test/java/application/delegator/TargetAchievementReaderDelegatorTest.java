package application.delegator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import adapter.console.ui.BufferedWriterBasedGuidePrinter;
import adapter.ui.GuidePrinter;
import application.builder.DefaultInvestmentRequestBuilder;
import application.builder.InvestmentRequestBuilder;
import application.reader.TargetAchievementRequestReader;
import application.request.TargetAchievementRequest;
import co.invest72.investment.domain.tax.TaxType;

class TargetAchievementReaderDelegatorTest {
	private GuidePrinter guidePrinter;
	private InvestmentRequestBuilder investmentRequestBuilder;

	public static Stream<Arguments> targetAchievementInputFileSource() {
		TargetAchievementRequest expected1 = TargetAchievementRequest.builder()
			.targetAmount(10_000_000)
			.monthlyInvestmentAmount(1_000_000)
			.interestRate(0.05)
			.taxType(TaxType.STANDARD.getDescription())
			.taxRate(0.154)
			.build();
		TargetAchievementRequest expected2 = TargetAchievementRequest.builder()
			.targetAmount(10_000_000)
			.monthlyInvestmentAmount(1_000_000)
			.interestRate(0.05)
			.taxType(TaxType.NON_TAX.getDescription())
			.taxRate(0.0)
			.build();
		return Stream.of(
			Arguments.of("src/test/resources/target_achievement_case/case1/target_achievement_test_input1.txt",
				expected1),
			Arguments.of("src/test/resources/target_achievement_case/case2/target_achievement_test_input2.txt",
				expected2)
		);
	}

	@BeforeEach
	void setUp() {
		investmentRequestBuilder = new DefaultInvestmentRequestBuilder();
		guidePrinter = new BufferedWriterBasedGuidePrinter(
			new BufferedWriter(new OutputStreamWriter(System.out)));
	}

	@ParameterizedTest
	@MethodSource(value = "targetAchievementInputFileSource")
	void readInvestmentRequest(String inputFilePath, TargetAchievementRequest expected) throws IOException {
		InputStream in = new FileInputStream(inputFilePath);
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
		TargetAchievementRequestReader reader = new TargetAchievementRequestReader(bufferedReader, guidePrinter);
		InvestmentReaderDelegator<TargetAchievementRequest> delegator = new TargetAchievementReaderDelegator(
			investmentRequestBuilder, reader);

		TargetAchievementRequest request = delegator.readInvestmentRequest();

		Assertions.assertNotNull(request);
		Assertions.assertEquals(expected.initialCapital(), request.initialCapital());
		Assertions.assertEquals(expected.targetAmount(), request.targetAmount());
		Assertions.assertEquals(expected.monthlyInvestmentAmount(), request.monthlyInvestmentAmount());
		Assertions.assertEquals(expected.interestRate(), request.interestRate());
		Assertions.assertEquals(expected.taxType(), request.taxType());
		Assertions.assertEquals(expected.taxRate(), request.taxRate());
	}
}
