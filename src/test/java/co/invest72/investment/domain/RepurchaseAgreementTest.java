package co.invest72.investment.domain;

import java.math.BigDecimal;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import co.invest72.investment.domain.amount.FixedDepositAmount;
import co.invest72.investment.domain.interest.AnnualInterestRate;
import co.invest72.investment.domain.period.YearlyInvestPeriod;
import co.invest72.investment.domain.tax.FixedTaxRate;
import co.invest72.investment.domain.tax.StandardTax;
import co.invest72.investment.domain.tax.TaxType;
import co.invest72.money.domain.Money;

class RepurchaseAgreementTest {

	private Investment investment;

	@BeforeEach
	void setUp() {
		InvestmentAmount amount = new FixedDepositAmount(Money.won(1_000_000));
		InterestRate interestRate = new AnnualInterestRate(BigDecimal.valueOf(0.05));
		InvestPeriod investPeriod = new YearlyInvestPeriod(1);
		Taxable taxable = new StandardTax(new FixedTaxRate(BigDecimal.valueOf(0.154)));
		investment = new RepurchaseAgreement(amount, interestRate, investPeriod, taxable);
	}

	@Test
	@DisplayName("원금 계산")
	void should_return_principal() {
		// when
		Money principal = investment.getPrincipal();
		// then
		Assertions.assertThat(principal).isEqualTo(Money.won(1_046_800));
	}

	@Test
	@DisplayName("특정 개월 수 원금 계산")
	void should_not_change_principal_when_change_month() {
		// when & then
		Assertions.assertThat(investment.getPrincipal(-1)).isEqualTo(Money.won(1_000_000));
		Assertions.assertThat(investment.getPrincipal(0)).isEqualTo(Money.won(1_000_000));
		Assertions.assertThat(investment.getPrincipal(1)).isEqualTo(Money.won(1_000_000));
		Assertions.assertThat(investment.getPrincipal(2)).isEqualTo(Money.won(1_004_167));
		Assertions.assertThat(investment.getPrincipal(3)).isEqualTo(Money.won(1_008_351));
		Assertions.assertThat(investment.getPrincipal(4)).isEqualTo(Money.won(1_012_552));
		Assertions.assertThat(investment.getPrincipal(5)).isEqualTo(Money.won(1_016_771));
		Assertions.assertThat(investment.getPrincipal(6)).isEqualTo(Money.won(1_021_008));
		Assertions.assertThat(investment.getPrincipal(7)).isEqualTo(Money.won(1_025_262));
		Assertions.assertThat(investment.getPrincipal(8)).isEqualTo(Money.won(1_029_534));
		Assertions.assertThat(investment.getPrincipal(9)).isEqualTo(Money.won(1_033_824));
		Assertions.assertThat(investment.getPrincipal(10)).isEqualTo(Money.won(1_038_131));
		Assertions.assertThat(investment.getPrincipal(11)).isEqualTo(Money.won(1_042_457));
		Assertions.assertThat(investment.getPrincipal(12)).isEqualTo(Money.won(1_046_800));
		Assertions.assertThat(investment.getPrincipal(13)).isEqualTo(Money.won(1_046_800));
	}

	@Test
	@DisplayName("만기 이자 계산")
	void should_return_interest() {
		// when & then
		Assertions.assertThat(investment.getInterest()).isEqualTo(Money.won(4_362));
	}

	@Test
	@DisplayName("특정 개월수의 이자 계산 - 연이율5%")
	void should_return_interest_when_annual_interest_is_five_percent() {
		// when & then
		Assertions.assertThat(investment.getInterest(-1)).isEqualTo(Money.won(0));
		Assertions.assertThat(investment.getInterest(0)).isEqualTo(Money.won(0));
		Assertions.assertThat(investment.getInterest(1)).isEqualTo(Money.won(4_167));
		Assertions.assertThat(investment.getInterest(2)).isEqualTo(Money.won(4_184));
		Assertions.assertThat(investment.getInterest(3)).isEqualTo(Money.won(4_201));
		Assertions.assertThat(investment.getInterest(4)).isEqualTo(Money.won(4_219));
		Assertions.assertThat(investment.getInterest(5)).isEqualTo(Money.won(4_237));
		Assertions.assertThat(investment.getInterest(6)).isEqualTo(Money.won(4_254));
		Assertions.assertThat(investment.getInterest(7)).isEqualTo(Money.won(4_272));
		Assertions.assertThat(investment.getInterest(8)).isEqualTo(Money.won(4_290));
		Assertions.assertThat(investment.getInterest(9)).isEqualTo(Money.won(4_308));
		Assertions.assertThat(investment.getInterest(10)).isEqualTo(Money.won(4_326));
		Assertions.assertThat(investment.getInterest(11)).isEqualTo(Money.won(4_344));
		Assertions.assertThat(investment.getInterest(12)).isEqualTo(Money.won(4_362));
		Assertions.assertThat(investment.getInterest(13)).isEqualTo(Money.won(4_362));
	}

	@Test
	@DisplayName("이자 계산 - 연이율5%, 투자기간이 12개월이고, month가 13인 경우 12개월 시점의 이자를 계산하여야 한다")
	void should_return_expiration_interest_when_invest_period_is_12_month_and_month_is_13() {
		Assertions.assertThat(investment.getInterest(13)).isEqualTo(Money.won(4_362));
	}

	@Test
	@DisplayName("이자 계산 - 연이율10%, 첫번째 달 이자 계산")
	void should_return_interest_when_annual_interest_ten_percent_and_month_is_first() {
		// given
		InvestmentAmount amount = new FixedDepositAmount(Money.won(1_000_000));
		InterestRate interestRate = new AnnualInterestRate(BigDecimal.valueOf(0.1));
		InvestPeriod investPeriod = new YearlyInvestPeriod(1);
		Taxable taxable = new StandardTax(new FixedTaxRate(BigDecimal.valueOf(0.154)));
		investment = new RepurchaseAgreement(amount, interestRate, investPeriod, taxable);
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
		Taxable taxable = new StandardTax(new FixedTaxRate(BigDecimal.valueOf(0.154)));
		investment = new RepurchaseAgreement(amount, interestRate, investPeriod, taxable);
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
		Assertions.assertThat(profit).isEqualTo(Money.won(1_051_162));
	}

	@Test
	@DisplayName("월별 총 수익 계산")
	void should_return_profit_given_months() {
		// when & then
		Assertions.assertThat(investment.getProfit(-1)).isEqualTo(Money.won(1_000_000));
		Assertions.assertThat(investment.getProfit(0)).isEqualTo(Money.won(1_000_000));
		Assertions.assertThat(investment.getProfit(1)).isEqualTo(Money.won(1_004_167));
		Assertions.assertThat(investment.getProfit(2)).isEqualTo(Money.won(1_008_351));
		Assertions.assertThat(investment.getProfit(3)).isEqualTo(Money.won(1_012_552));
		Assertions.assertThat(investment.getProfit(4)).isEqualTo(Money.won(1_016_771));
		Assertions.assertThat(investment.getProfit(5)).isEqualTo(Money.won(1_021_008));
		Assertions.assertThat(investment.getProfit(6)).isEqualTo(Money.won(1_025_262));
		Assertions.assertThat(investment.getProfit(7)).isEqualTo(Money.won(1_029_534));
		Assertions.assertThat(investment.getProfit(8)).isEqualTo(Money.won(1_033_824));
		Assertions.assertThat(investment.getProfit(9)).isEqualTo(Money.won(1_038_131));
		Assertions.assertThat(investment.getProfit(10)).isEqualTo(Money.won(1_042_457));
		Assertions.assertThat(investment.getProfit(11)).isEqualTo(Money.won(1_046_800));
		Assertions.assertThat(investment.getProfit(12)).isEqualTo(Money.won(1_051_162));
		Assertions.assertThat(investment.getProfit(13)).isEqualTo(Money.won(1_051_162));
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
		Assertions.assertThat(totalInterest).isEqualTo(Money.won(51_162));
	}

	@Test
	@DisplayName("총 세금 계산")
	void should_return_total_tax() {
		// when
		Money totalTax = investment.getTotalTax();
		// then
		Assertions.assertThat(totalTax).isEqualTo(Money.won(7_879));
	}

	@Test
	@DisplayName("만기 총수익 계산")
	void should_return_total_profit() {
		// when
		Money totalProfit = investment.getTotalProfit();
		// then
		Assertions.assertThat(totalProfit).isEqualTo(Money.won(1_043_283));
	}

	@Test
	@DisplayName("세금 종류 반환")
	void should_return_tax_type() {
		// when
		String taxType = investment.getTaxType();
		// then
		Assertions.assertThat(taxType).isEqualTo(TaxType.STANDARD.getDescription());
	}

	@Test
	@DisplayName("년도별 원금 계산")
	void should_return_principal_when_years_is_one() {
		// given
		investment = ((RepurchaseAgreement)investment).toBuilder()
			.investPeriod(new YearlyInvestPeriod(5))
			.build();
		// when & then
		Assertions.assertThat(investment.getPrincipalForYear(-1)).isEqualTo(Money.won(1_000_000));
		Assertions.assertThat(investment.getPrincipalForYear(0)).isEqualTo(Money.won(1_000_000));
		Assertions.assertThat(investment.getPrincipalForYear(1)).isEqualTo(Money.won(1_000_000));
		Assertions.assertThat(investment.getPrincipalForYear(2)).isEqualTo(Money.won(1_051_162));
		Assertions.assertThat(investment.getPrincipalForYear(3)).isEqualTo(Money.won(1_104_941));
		Assertions.assertThat(investment.getPrincipalForYear(4)).isEqualTo(Money.won(1_161_472));
		Assertions.assertThat(investment.getPrincipalForYear(5)).isEqualTo(Money.won(1_220_895));
	}
}
