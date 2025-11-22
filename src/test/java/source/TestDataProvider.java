package source;

import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

public class TestDataProvider {
	public static Stream<Arguments> getPrincipalWithMonthSource() {
		return Stream.of(
			Arguments.of(-1, 0),
			Arguments.of(0, 0),
			Arguments.of(1, 0),
			Arguments.of(12, 11_232_055)
		);
	}

	public static Stream<Arguments> getInterestWithMonthSource() {
		return Stream.of(
			Arguments.of(-1, 0),
			Arguments.of(0, 0),
			Arguments.of(1, 0),
			Arguments.of(2, 4_167),
			Arguments.of(12, 46_800)
		);
	}

	public static Stream<Arguments> getTotalProfitWithMonthSource() {
		return Stream.of(
			Arguments.of(-1, 0),
			Arguments.of(0, 0),
			Arguments.of(1, 0),
			Arguments.of(2, 1_004_167),
			Arguments.of(12, 11_278_855)
		);
	}
}
