package util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class TestFileUtilsTest {

	@Test
	void readCsvFile() {
		String filePath = "src/test/resources/calculate_investment_request/error_data.csv";

		List<Map<String, Object>> actual = TestFileUtils.readCsvFile(filePath);

		List<Map<String, Object>> expected = new ArrayList<>();
		Map<String, Object> row1 = Map.of(
			"type", "예금",
			"amountType", "일시불",
			"amount", 1000000,
			"periodType", "년",
			"periodValue", 1,
			"interestType", "단리",
			"annualInterestRate", 0.05,
			"taxType", "일반과세",
			"taxRate", 0.154
		);
		expected.add(row1);
		Assertions.assertThat(actual).isEqualTo(expected);
	}
}
