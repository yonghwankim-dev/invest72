package domain.investment;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
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

	@ParameterizedTest
	@CsvFileSource(files = "src/test/resources/compound_fixed_installment_saving_1y_5percent_standard_tax.csv", numLinesToSkip = 1)
	void shouldReturnInvestmentAmount(int month, int expectedPrincipal, int expectedInterest, int expectedTax,
		int expectedTotalProfit) {
		int principal = investment.getPrincipal(month);
		int interest = investment.getInterest(month);
		int tax = investment.getTax(month);
		int totalProfit = investment.getTotalProfit(month);

		assertEquals(expectedPrincipal, principal);
		assertEquals(expectedInterest, interest);
		assertEquals(expectedTax, tax);
		assertEquals(expectedTotalProfit, totalProfit);
	}

	@Test
	void created() {
		investment = new CompoundFixedInstallmentSaving(investmentAmount, investPeriod, annualInterestRateRate,
			taxable);
		Assertions.assertNotNull(investment);
	}

	@Test
	void shouldReturnAmount() {
		int amount = investment.getTotalProfit();

		int expectedTotalPrincipal = 12_000_000;
		int expectedInterest = 330_017;
		int expectedBalanceValue = expectedTotalPrincipal + expectedInterest;

		assertEquals(expectedBalanceValue, amount);
	}

	@Test
	void shouldReturnAmount_whenInvestmentPeriodIs6() {
		investPeriod = new MonthlyInvestPeriod(6);
		investment = new CompoundFixedInstallmentSaving(investmentAmount, investPeriod, annualInterestRateRate,
			taxable);

		int amount = investment.getTotalProfit();

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

		int amount = investment.getTotalProfit();

		int expectedBalanceValue = 0;
		assertEquals(expectedBalanceValue, amount);
	}

	@Test
	void shouldReturnAmount_whenInvestmentPeriodIs0() {
		investPeriod = new MonthlyInvestPeriod(0);
		investment = new CompoundFixedInstallmentSaving(investmentAmount, investPeriod, annualInterestRateRate,
			taxable);

		int amount = investment.getTotalProfit();

		int expectedBalanceValue = 0;
		assertEquals(expectedBalanceValue, amount);
	}

	@Test
	void shouldReturnAmount_whenAnnualInterestRateIsZero() {
		annualInterestRateRate = new AnnualInterestRate(0.0);
		investment = new CompoundFixedInstallmentSaving(investmentAmount, investPeriod, annualInterestRateRate,
			taxable);

		int amount = investment.getTotalProfit();

		int expectedBalanceValue = 12_000_000;
		assertEquals(expectedBalanceValue, amount);
	}

	@Test
	void shouldReturnTaxedAmount_whenTaxTypeIsTaxable() {
		int months = 120; // 10년
		investPeriod = new MonthlyInvestPeriod(months);
		taxable = taxableFactory.createStandardTax(new FixedTaxRate(0.154));
		investment = new CompoundFixedInstallmentSaving(investmentAmount, investPeriod, annualInterestRateRate,
			taxable);

		int amount = investment.getTotalProfit();

		int expectedBalanceValue = 150_396_178;
		assertEquals(expectedBalanceValue, amount);
	}

	@Test
	void shouldReturnAmount_whenInvestmentAmountIsYearly() {
		investmentAmount = new YearlyInstallmentInvestmentAmount(12_000_000);
		investment = new CompoundFixedInstallmentSaving(investmentAmount, investPeriod, annualInterestRateRate,
			taxable);

		int amount = investment.getTotalProfit();

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

		int amount = investment.getTotalProfit();

		int expectedAmount = 155_929_288;
		assertEquals(expectedAmount, amount);
	}

	@Test
	void shouldReturnAmount_whenTaxableIsTaxBenefit() {
		taxable = taxableFactory.createTaxBenefit(new FixedTaxRate(0.014));
		investment = new CompoundFixedInstallmentSaving(investmentAmount, investPeriod, annualInterestRateRate,
			taxable);

		int amount = investment.getTotalProfit();

		int expectedAmount = 12_325_397;
		assertEquals(expectedAmount, amount);
	}

	@Test
	void shouldReturnPrincipalAmount() {
		int principalAmount = investment.getPrincipal();

		int expectedPrincipalAmount = 12_000_000;
		assertEquals(expectedPrincipalAmount, principalAmount);
	}

	@Test
	void shouldReturnInterest() {
		int interest = investment.getInterest();

		int expectedInterest = 330_017;
		assertEquals(expectedInterest, interest);
	}

	@Test
	void shouldReturnTax() {
		int tax = investment.getTax();

		int expectedTax = 0; // NonTaxable
		assertEquals(expectedTax, tax);
	}

	@Test
	void shouldReturnAccumulatedPrincipal() {
		int month = 12;

		int principalAmount = investment.getPrincipal(month);

		int expectedPrincipalAmount = 12_000_000;
		assertEquals(expectedPrincipalAmount, principalAmount);
	}

	@ParameterizedTest
	@ValueSource(ints = {0, 13})
	void shouldThrowExceptionForAccumulatedPrincipal_whenInvalidMonth(int month) {
		Assertions.assertThrows(IllegalArgumentException.class, () -> investment.getPrincipal(month));
	}

	@Test
	void shouldReturnAccumulatedInterest() {
		int month = 12;

		int accumulatedInterest = investment.getInterest(month);

		int expectedAccumulatedInterest = 330_017;
		assertEquals(expectedAccumulatedInterest, accumulatedInterest);
	}

	@ParameterizedTest
	@ValueSource(ints = {0, 13})
	void shouldThrowExceptionForGetAccumulatedInterest_whenInvalidMonth(int month) {
		Assertions.assertThrows(IllegalArgumentException.class, () -> investment.getInterest(month));
	}

	@Test
	void shouldReturnAccumulatedTax() {
		taxable = taxableFactory.createStandardTax(new FixedTaxRate(0.154));
		investment = new CompoundFixedInstallmentSaving(
			investmentAmount,
			investPeriod,
			annualInterestRateRate,
			taxable
		);
		int month = 12;

		int accumulatedTax = investment.getTax(month);

		int expectedAccumulatedTax = 50_823; // 330,017 * 0.154
		assertEquals(expectedAccumulatedTax, accumulatedTax);
	}

	@ParameterizedTest
	@ValueSource(ints = {0, 13})
	void shouldThrowExceptionForGetAccumulatedTax_whenInvalidMonth(int month) {
		Assertions.assertThrows(IllegalArgumentException.class, () -> investment.getTax(month));
	}

	@Test
	void shouldReturnAccumulatedTotalProfit() {
		int month = 12;

		int accumulatedTotalProfit = investment.getTotalProfit(month);

		int expectedAccumulatedTotalProfit = 12_330_017; // 12,000,000 + 330,017 - 0
		assertEquals(expectedAccumulatedTotalProfit, accumulatedTotalProfit);
	}

	@ParameterizedTest
	@ValueSource(ints = {0, 13})
	void shouldThrowExceptionForGetAccumulatedTotalProfit_whenInvalidMonth(int month) {
		Assertions.assertThrows(IllegalArgumentException.class, () -> investment.getTotalProfit(month));
	}

	@Test
	void shouldReturnFinalMonth() {
		int finalMonth = investment.getFinalMonth();

		int expectedFinalMonth = 12;
		assertEquals(expectedFinalMonth, finalMonth);
	}
}
