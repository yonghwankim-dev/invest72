package source;

import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

public class TestDataProvider {
	public static Stream<Arguments> validMonths() {
		return Stream.of(
			Arguments.of(0, 0),
			Arguments.of(1, 0),
			Arguments.of(12, 11_000_000)
		);
	}
}
