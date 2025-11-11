package util;

import static java.nio.file.Files.*;
import static java.nio.file.Paths.*;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

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
		CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
			.setHeader()
			.setSkipHeaderRecord(true)
			.setIgnoreHeaderCase(true)
			.setTrim(true)
			.get();
		try (FileReader reader = new FileReader(filePath);
			 CSVParser csvParser = CSVParser.builder()
				 .setReader(reader)
				 .setFormat(csvFormat)
				 .get()
		) {
			for (CSVRecord csvRecord : csvParser) {
				Map<String, Object> jsonMap = new LinkedHashMap<>();
				for (String header : csvParser.getHeaderMap().keySet()) {
					String value = csvRecord.get(header);

					// null 처리
					if ("null".equalsIgnoreCase(value)) {
						jsonMap.put(header, null);
					} else {
						// 숫자 타입으로 변환 시도
						try {
							if (value.contains(".")) {
								jsonMap.put(header, Double.parseDouble(value));
							} else {
								jsonMap.put(header, Integer.parseInt(value));
							}
						} catch (NumberFormatException e) {
							jsonMap.put(header, value);
						}
					}
				}
				result.add(jsonMap);
			}
		} catch (Exception e) {
			throw new IllegalArgumentException("Error reading CSV file: " + filePath, e);
		}
		return result;
	}
}
