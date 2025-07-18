package util;

import static java.nio.file.Files.*;
import static java.nio.file.Paths.*;

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
}
