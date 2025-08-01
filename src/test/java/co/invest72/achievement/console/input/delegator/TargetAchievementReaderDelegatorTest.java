package co.invest72.achievement.console.input.delegator;

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

import co.invest72.achievement.application.CalculateAchievement;
import co.invest72.achievement.console.input.reader.TargetAchievementRequestReader;
import co.invest72.investment.console.output.guide.BufferedWriterBasedGuidePrinter;
import co.invest72.investment.console.output.guide.GuidePrinter;
import co.invest72.investment.domain.tax.TaxType;

class TargetAchievementReaderDelegatorTest {
	private GuidePrinter guidePrinter;

	public static Stream<Arguments> targetAchievementInputFileSource() {
		CalculateAchievement.AchievementRequest expected1 = CalculateAchievement.AchievementRequest.builder()
			.targetAmount(10_000_000)
			.monthlyInvestmentAmount(1_000_000)
			.interestRate(0.05)
			.taxType(TaxType.STANDARD.getDescription())
			.taxRate(0.154)
			.build();
		CalculateAchievement.AchievementRequest expected2 = CalculateAchievement.AchievementRequest.builder()
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
		guidePrinter = new BufferedWriterBasedGuidePrinter(
			new BufferedWriter(new OutputStreamWriter(System.out)));
	}

	@ParameterizedTest
	@MethodSource(value = "targetAchievementInputFileSource")
	void readInvestmentRequest(String inputFilePath, CalculateAchievement.AchievementRequest expected) throws
		IOException {
		InputStream in = new FileInputStream(inputFilePath);
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
		TargetAchievementRequestReader reader = new TargetAchievementRequestReader(bufferedReader, guidePrinter);
		TargetAchievementReaderDelegator delegator = new TargetAchievementReaderDelegator(reader);

		CalculateAchievement.AchievementRequest request = delegator.readRequest();

		Assertions.assertNotNull(request);
		Assertions.assertEquals(expected.initialCapital(), request.initialCapital());
		Assertions.assertEquals(expected.targetAmount(), request.targetAmount());
		Assertions.assertEquals(expected.monthlyInvestmentAmount(), request.monthlyInvestmentAmount());
		Assertions.assertEquals(expected.interestRate(), request.interestRate());
		Assertions.assertEquals(expected.taxType(), request.taxType());
		Assertions.assertEquals(expected.taxRate(), request.taxRate());
	}
}
