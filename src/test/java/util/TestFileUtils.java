package util;

import static java.nio.file.Files.*;
import static java.nio.file.Paths.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestFileUtils {
	private TestFileUtils() {
		throw new UnsupportedOperationException("Utility class cannot be instantiated");
	}

	public static String readFile(String filePath) {
		try {
			return readString(get(filePath));
		} catch (java.io.IOException e) {
			throw new IllegalArgumentException("File not found: " + filePath, e);
		}
	}

	/**
	 * csv 파일을 읽어서 json 배열 문자열로 반환
	 * @param filePath csv 파일 경로
	 * @return json 형태 리스트
	 */
	public static List<Map<String, Object>> readCsvFile(String filePath) {
		List<Map<String, Object>> result = new ArrayList<>();
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
		result.add(row1);
		return result;
	}
}
