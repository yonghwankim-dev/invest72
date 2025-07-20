package domain.investment;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import domain.amount.InstallmentInvestmentAmount;
import domain.amount.MonthlyInstallmentInvestmentAmount;
import domain.amount.YearlyInstallmentInvestmentAmount;
import domain.interest_rate.AnnualInterestRate;
import domain.interest_rate.InterestRate;
import domain.invest_period.InvestPeriod;
import domain.invest_period.MonthlyInvestPeriod;
import domain.invest_period.YearlyInvestPeriod;
import domain.tax.FixedTaxRate;
import domain.tax.Taxable;
import domain.tax.factory.KoreanTaxableFactory;
import domain.tax.factory.TaxableFactory;

class CompoundFixedInstallmentSavingTest {

	private InstallmentInvestmentAmount investmentAmount; // 월 투자 금액(원)
	private InvestPeriod investPeriod; // 투자 기간
	private InterestRate annualInterestRateRate; // 연 수익율
	private TaxableFactory taxableFactory;
	private Taxable taxable; // 세금 적용 방식
	private CompoundFixedInstallmentSaving investment;

	@BeforeEach
	void setUp() {
		investmentAmount = new MonthlyInstallmentInvestmentAmount(1_000_000);
		investPeriod = new MonthlyInvestPeriod(12);
		annualInterestRateRate = new AnnualInterestRate(0.05);
		taxableFactory = new KoreanTaxableFactory();
		taxable = taxableFactory.createNonTax();
		investment = new CompoundFixedInstallmentSaving(investmentAmount, investPeriod, annualInterestRateRate,
			taxable);

	}

	@Test
	void created() {
		investment = new CompoundFixedInstallmentSaving(investmentAmount, investPeriod, annualInterestRateRate,
			taxable);
		Assertions.assertNotNull(investment);
	}

	@Test
	void shouldReturnBalance() {
		int amount = investment.getAmount();

		int expectedTotalPrincipal = 12_000_000;
		int expectedInterest = 330_017;
		int expectedBalanceValue = expectedTotalPrincipal + expectedInterest;

		assertEquals(expectedBalanceValue, amount);
	}

	@Test
	void shouldReturnBalance_whenInvestmentPeriodIs6() {
		investPeriod = new MonthlyInvestPeriod(6);
		investment = new CompoundFixedInstallmentSaving(investmentAmount, investPeriod, annualInterestRateRate,
			taxable);

		int amount = investment.getAmount();

		int expectedTotalPrincipal = 6_000_000;
		int expectedInterest = 88_110;
		int expectedBalanceValue = expectedTotalPrincipal + expectedInterest;
		assertEquals(expectedBalanceValue, amount);
	}

	@Test
	void shouldReturnZero_whenMonthlyInvestmentIsZero() {
		investmentAmount = new MonthlyInstallmentInvestmentAmount(0);
		investment = new CompoundFixedInstallmentSaving(investmentAmount, investPeriod, annualInterestRateRate,
			taxable);

		int amount = investment.getAmount();

		int expectedBalanceValue = 0;
		assertEquals(expectedBalanceValue, amount);
	}

	@Test
	void shouldReturnBalance_whenInvestmentPeriodIs0() {
		investPeriod = new MonthlyInvestPeriod(0);
		investment = new CompoundFixedInstallmentSaving(investmentAmount, investPeriod, annualInterestRateRate,
			taxable);

		int amount = investment.getAmount();

		int expectedBalanceValue = 0;
		assertEquals(expectedBalanceValue, amount);
	}

	@Test
	void shouldReturnBalance_whenAnnualInterestRateIsZero() {
		annualInterestRateRate = new AnnualInterestRate(0.0);
		investment = new CompoundFixedInstallmentSaving(investmentAmount, investPeriod, annualInterestRateRate,
			taxable);

		int amount = investment.getAmount();

		int expectedBalanceValue = 12_000_000;
		assertEquals(expectedBalanceValue, amount);
	}

	@Test
	void shouldReturnTaxedBalance_whenTaxTypeIsTaxable() {
		int months = 120; // 10년
		investPeriod = new MonthlyInvestPeriod(months);
		taxable = taxableFactory.createStandardTax(new FixedTaxRate(0.154));
		investment = new CompoundFixedInstallmentSaving(investmentAmount, investPeriod, annualInterestRateRate,
			taxable);

		int amount = investment.getAmount();

		int expectedBalanceValue = 150_396_178;
		assertEquals(expectedBalanceValue, amount);
	}

	@Test
	void shouldReturnAmount_whenInvestmentAmountIsYearly() {
		investmentAmount = new YearlyInstallmentInvestmentAmount(12_000_000);
		investment = new CompoundFixedInstallmentSaving(investmentAmount, investPeriod, annualInterestRateRate,
			taxable);

		int amount = investment.getAmount();

		int expectedPrincipal = 12_000_000;
		int expectedInterest = 330_017;
		int expectedAmount = expectedPrincipal + expectedInterest;
		assertEquals(expectedAmount, amount);
	}

	@Test
	void shouldReturnAmount_whenInvestPeriodIsYearly() {
		investPeriod = new YearlyInvestPeriod(10);
		investment = new CompoundFixedInstallmentSaving(investmentAmount, investPeriod, annualInterestRateRate,
			taxable);

		int amount = investment.getAmount();

		int expectedAmount = 155_929_288;
		assertEquals(expectedAmount, amount);
	}

	@Test
	void shouldReturnAmount_whenTaxableIsTaxBenefit() {
		taxable = taxableFactory.createTaxBenefit(new FixedTaxRate(0.014));
		investment = new CompoundFixedInstallmentSaving(investmentAmount, investPeriod, annualInterestRateRate,
			taxable);

		int amount = investment.getAmount();

		int expectedAmount = 12_325_397;
		assertEquals(expectedAmount, amount);
	}

	@Test
	void shouldReturnAccumulatedPrincipal() {
		int month = 12;

		int principalAmount = investment.getAccumulatedPrincipal(month);

		int expectedPrincipalAmount = 12_000_000;
		assertEquals(expectedPrincipalAmount, principalAmount);
	}

	@ParameterizedTest
	@ValueSource(ints = {0, 13})
	void shouldThrowExceptionForAccumulatedPrincipal_whenInvalidMonth(int month) {
		Assertions.assertThrows(IllegalArgumentException.class, () -> investment.getAccumulatedPrincipal(month));
	}
}
