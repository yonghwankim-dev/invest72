package application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class KoreanStringBasedInvestPeriodFactoryTest {

	@Test
	void created() {
		InvestPeriodFactory investPeriodFactory = new KoreanStringBasedInvestPeriodFactory();

		Assertions.assertNotNull(investPeriodFactory);
	}
}
