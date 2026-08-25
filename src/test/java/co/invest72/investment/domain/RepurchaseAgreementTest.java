package co.invest72.investment.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RepurchaseAgreementTest {

	@Test
	@DisplayName("객체 생성")
	void canCreated() {
		// when
		Investment investment = new RepurchaseAgreement();
		// then
		Assertions.assertThat(investment).isNotNull();
	}
}
