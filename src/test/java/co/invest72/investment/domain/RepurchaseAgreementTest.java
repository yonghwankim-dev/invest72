package co.invest72.investment.domain;

import java.math.BigDecimal;
import java.util.stream.IntStream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import co.invest72.investment.domain.amount.FixedDepositAmount;
import co.invest72.investment.domain.interest.AnnualInterestRate;
import co.invest72.investment.domain.period.YearlyInvestPeriod;
import co.invest72.money.domain.Money;

class RepurchaseAgreementTest {

	private Investment investment;

	@BeforeEach
	void setUp() {
		InvestmentAmount amount = new FixedDepositAmount(Money.won(1_000_000));
		InterestRate interestRate = new AnnualInterestRate(BigDecimal.valueOf(0.05));
		InvestPeriod investPeriod = new YearlyInvestPeriod(1);
		investment = new RepurchaseAgreement(amount, interestRate, investPeriod);
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
		// when & then
		Assertions.assertThat(investment.getInterest()).isEqualTo(Money.won(50_000));
	}

	@Test
	@DisplayName("특정 개월수의 이자 계산 - 연이율5%")
	void should_return_interest_when_annual_interest_is_five_percent() {
		// when & then
		Assertions.assertThat(investment.getInterest(0)).isEqualTo(Money.won(0));
		Assertions.assertThat(investment.getInterest(1)).isEqualTo(Money.won(4_167));
		Assertions.assertThat(investment.getInterest(2)).isEqualTo(Money.won(8_333));
		Assertions.assertThat(investment.getInterest(12)).isEqualTo(Money.won(50_000));
	}

	@Test
	@DisplayName("이자 계산 - 연이율5%, 투자기간이 12개월이고, month가 13인 경우 12개월 만기 시의 이자를 반환해야 한다")
	void should_return_expiration_interest_when_invest_period_is_12_month_and_month_is_13() {
		Assertions.assertThat(investment.getInterest(13)).isEqualTo(Money.won(50_000));
	}

	@Test
	@DisplayName("이자 계산 - 연이율10%, 첫번째 달 이자 계산")
	void should_return_interest_when_annual_interest_ten_percent_and_month_is_first() {
		// given
		InvestmentAmount amount = new FixedDepositAmount(Money.won(1_000_000));
		InterestRate interestRate = new AnnualInterestRate(BigDecimal.valueOf(0.1));
		InvestPeriod investPeriod = new YearlyInvestPeriod(1);
		investment = new RepurchaseAgreement(amount, interestRate, investPeriod);
		// when
		Money interest = investment.getInterest(1);
		// then
		Assertions.assertThat(interest).isEqualTo(Money.won(8_333));
	}

	@Test
	@DisplayName("만기 개월수 반환")
	void should_return_expiration_month() {
		// when
		int finalMonth = investment.getFinalMonth();
		// then
		Assertions.assertThat(finalMonth).isEqualTo(12);
	}

	@Test
	@DisplayName("만기 개월수 반환 - 투자기간이 2년인 경우 24를 반환해야 한다")
	void should_return_24_month_when_invest_period_is_24_month() {
		// given
		InvestmentAmount amount = new FixedDepositAmount(Money.won(1_000_000));
		InterestRate interestRate = new AnnualInterestRate(BigDecimal.valueOf(0.1));
		InvestPeriod investPeriod = new YearlyInvestPeriod(2);
		investment = new RepurchaseAgreement(amount, interestRate, investPeriod);
		// when
		int finalMonth = investment.getFinalMonth();
		// then
		Assertions.assertThat(finalMonth).isEqualTo(24);
	}

	@Test
	@DisplayName("총 수익 계산 - 만기 총 수익인 경우")
	void should_return_profit_when_month_is_expiration() {
		// when
		Money profit = investment.getProfit();
		// then
		Assertions.assertThat(profit).isEqualTo(Money.won(1_050_000));
	}

	@Test
	@DisplayName("월별 총 수익 계산")
	void should_return_profit_given_months() {
		// when & then
		Assertions.assertThat(investment.getProfit(0)).isEqualTo(Money.won(1_000_000));
		Assertions.assertThat(investment.getProfit(1)).isEqualTo(Money.won(1_004_167));
		Assertions.assertThat(investment.getProfit(2)).isEqualTo(Money.won(1_008_333));
		Assertions.assertThat(investment.getProfit(12)).isEqualTo(Money.won(1_050_000));
		Assertions.assertThat(investment.getProfit(13)).isEqualTo(Money.won(1_050_000));
	}

	@Test
	@DisplayName("총 투자금 계산 - RP는 원금만 반환한다")
	void should_return_total_investment() {
		// when
		Money totalInvestment = investment.getTotalInvestment();
		// then
		Assertions.assertThat(totalInvestment).isEqualTo(Money.won(1_000_000));
	}

	@Test
	@DisplayName("총 이자 금액 계산 - 만기 시점의 이자 금액 반환해야 한다")
	void should_return_total_interest() {
		// when
		Money totalInterest = investment.getTotalInterest();
		// then
		Assertions.assertThat(totalInterest).isEqualTo(Money.won(50_000));
	}
}
