package domain.investment;

import org.junit.jupiter.api.BeforeEach;

import domain.amount.FixedDepositAmount;
import domain.amount.LumpSumInvestmentAmount;
import domain.interest_rate.AnnualInterestRate;
import domain.interest_rate.InterestRate;
import domain.invest_period.InvestPeriod;
import domain.invest_period.YearlyInvestPeriod;
import domain.tax.FixedTaxRate;
import domain.tax.Taxable;
import domain.tax.factory.KoreanTaxableFactory;
import domain.tax.factory.TaxableFactory;

class CompoundFixedDepositTest {

	private LumpSumInvestmentAmount depositAmount;
	private InterestRate interestRate;
	private InvestPeriod investPeriod;
	private TaxableFactory taxableFactory;
	private Taxable taxable;
	private Investment investment;

	@BeforeEach
	void setUp() {
		depositAmount = new FixedDepositAmount(1_000_000);
		interestRate = new AnnualInterestRate(0.05);
		investPeriod = new YearlyInvestPeriod(1);
		taxableFactory = new KoreanTaxableFactory();
		taxable = taxableFactory.createStandardTax(new FixedTaxRate(0.154));
		investment = new CompoundFixedDeposit(
			depositAmount,
			investPeriod, interestRate,
			taxable
		);
	}
}
