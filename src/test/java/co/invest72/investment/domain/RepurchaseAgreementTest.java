package co.invest72.investment.domain;

import java.util.stream.IntStream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import co.invest72.investment.domain.amount.FixedDepositAmount;
import co.invest72.money.domain.Money;

class RepurchaseAgreementTest {

	private Investment investment;

	@BeforeEach
	void setUp() {
		InvestmentAmount amount = new FixedDepositAmount(Money.won(1_000_000));
		investment = new RepurchaseAgreement(amount);
	}

	@Test
	@DisplayName("원금 계산")
	void should_return_principal() {
		// when
		Money principal = investment.getPrincipal();
		// then
		Assertions.assertThat(principal).isEqualTo(Money.won(1_000_000));
	}

	@Test
	@DisplayName("원금 계산 - 어느 개월수를 입력해도 원금 금액은 변하지 않는다")
	void should_not_change_principal_when_change_month() {
		// when & then
		Money expected = Money.won(1_000_000);
		IntStream.rangeClosed(-1, 13)
			.mapToObj(month -> investment.getPrincipal(month))
			.forEach(principal -> Assertions.assertThat(principal).isEqualTo(expected));
	}

	@Test
	@DisplayName("만기 이자 계산")
	void should_return_interest() {
		// when
		Money interest = investment.getInterest();
		// then
		Assertions.assertThat(interest).isEqualTo(Money.won(50_000));
	}
}
