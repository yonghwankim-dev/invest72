package util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class TestFileUtilsTest {

	@Test
	void readCsvFile() {
		String filePath = "src/test/resources/sample_error_data.csv";

		List<Map<String, Object>> actual = TestFileUtils.readCsvFile(filePath);

		List<Map<String, Object>> expected = new ArrayList<>();
		Map<String, Object> row1 = new LinkedHashMap<>();
		row1.put("type", null);
		row1.put("amountType", "일시불");
		row1.put("amount", 1000000);
		row1.put("periodType", "년");
		row1.put("periodValue", 1);
		row1.put("interestType", "단리");
		row1.put("annualInterestRate", 0.05);
		row1.put("taxType", "일반과세");
		row1.put("taxRate", 0.154);
		expected.add(row1);

		Map<String, Object> row2 = new LinkedHashMap<>();
		row2.put("type", "");
		row2.put("amountType", "일시불");
		row2.put("amount", 1000000);
		row2.put("periodType", "년");
		row2.put("periodValue", 1);
		row2.put("interestType", "단리");
		row2.put("annualInterestRate", 0.05);
		row2.put("taxType", "일반과세");
		row2.put("taxRate", 0.154);
		expected.add(row2);
		
		Assertions.assertThat(actual).isEqualTo(expected);
	}
}
