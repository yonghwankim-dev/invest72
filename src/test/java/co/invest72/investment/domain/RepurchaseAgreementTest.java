package co.invest72.investment.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import co.invest72.investment.domain.amount.FixedDepositAmount;
import co.invest72.money.domain.Money;

class RepurchaseAgreementTest {

	@Test
	@DisplayName("객체 생성")
	void canCreated() {
		// when
		InvestmentAmount amount = new FixedDepositAmount(Money.won(1_000_000));
		Investment investment = new RepurchaseAgreement(amount);
		// then
		Assertions.assertThat(investment).isNotNull();
	}

	@Test
	@DisplayName("원금 계산")
	void should_return_principal() {
		// given
		InvestmentAmount amount = new FixedDepositAmount(Money.won(1_000_000));
		Investment investment = new RepurchaseAgreement(amount);
		// when
		Money principal = investment.getPrincipal();
		// then
		Assertions.assertThat(principal).isEqualTo(Money.won(1_000_000));
	}
}
