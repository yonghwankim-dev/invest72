package co.invest72.investment.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import co.invest72.money.domain.Money;

class RepurchaseAgreementTest {

	@Test
	@DisplayName("객체 생성")
	void canCreated() {
		// when
		Investment investment = new RepurchaseAgreement();
		// then
		Assertions.assertThat(investment).isNotNull();
	}

	@Test
	@DisplayName("원금 계산")
	void should_return_principal() {
		// given
		Investment investment = new RepurchaseAgreement();
		// when
		Money principal = investment.getPrincipal();
		// then
		Assertions.assertThat(principal).isEqualTo(Money.won(1_000_000));
	}
}
