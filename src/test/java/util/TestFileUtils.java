package util;

public class TestFileUtils {
	private TestFileUtils() {
		// Prevent instantiation
		throw new UnsupportedOperationException("Utility class cannot be instantiated");
	}

	public static String readFile(String filePath) {
		try {
			return new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(filePath)));
		} catch (java.io.IOException e) {
			throw new RuntimeException("Failed to read file: " + filePath, e);
		}
	}
}
