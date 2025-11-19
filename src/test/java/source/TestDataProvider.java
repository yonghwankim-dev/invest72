package source;

import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

public class TestDataProvider {
	public static Stream<Arguments> getPrincipalWithMonthSource() {
		return Stream.of(
			Arguments.of(-1, 0),
			Arguments.of(0, 0),
			Arguments.of(1, 0),
			Arguments.of(12, 11_000_000)
		);
	}

	public static Stream<Arguments> getInterestWithMonthSource() {
		return Stream.of(
			Arguments.of(-1, 0),
			Arguments.of(0, 0),
			Arguments.of(1, 0),
			Arguments.of(2, 4_167),
			Arguments.of(12, 278_855)
		);
	}

	public static Stream<Arguments> getTaxWithMonthSource() {
		return Stream.of(
			Arguments.of(-1, 0),
			Arguments.of(0, 0),
			Arguments.of(1, 0),
			Arguments.of(2, 642),
			Arguments.of(12, 42_944)
		);
	}
}
