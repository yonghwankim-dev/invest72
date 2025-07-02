package adapter.console.reader;

import java.io.BufferedReader;
import java.io.IOException;

import application.InvestmentRequest;
import application.TaxableResolver;
import domain.interest_rate.InterestRate;
import domain.invest_amount.InvestmentAmount;
import domain.invest_period.InvestPeriod;
import domain.invest_period.PeriodMonthsRange;
import domain.invest_period.PeriodRange;
import domain.invest_period.PeriodYearRange;
import domain.tax.TaxRate;
import domain.tax.Taxable;
import domain.type.InterestType;
import domain.type.InvestmentType;
import domain.type.PeriodType;
import domain.type.TaxType;

public class DefaultInvestmentReaderDelegator implements InvestmentReaderDelegator {

	private final InvestmentTypeReader investmentTypeReader;
	private final InvestmentAmountReaderDelegator investmentAmountReaderDelegator;
	private final PeriodTypeReader periodTypeReader;
	private final PeriodReader periodReader;
	private final InterestTypeReader interestTypeReader;
	private final InterestRatePercentReader interestRatePercentReader;
	private final TaxTypeReader taxTypeReader;
	private final TaxRateReader taxRateReader;

	public DefaultInvestmentReaderDelegator(InvestmentTypeReader investmentTypeReader,
		InvestmentAmountReaderDelegator investmentAmountReaderDelegator,
		PeriodTypeReader periodTypeReader, PeriodReader periodReader,
		InterestTypeReader interestTypeReader, InterestRatePercentReader interestRatePercentReader,
		TaxTypeReader taxTypeReader, TaxRateReader taxRateReader) {
		this.investmentTypeReader = investmentTypeReader;
		this.investmentAmountReaderDelegator = investmentAmountReaderDelegator;
		this.periodTypeReader = periodTypeReader;
		this.periodReader = periodReader;
		this.interestTypeReader = interestTypeReader;
		this.interestRatePercentReader = interestRatePercentReader;
		this.taxTypeReader = taxTypeReader;
		this.taxRateReader = taxRateReader;
	}

	private InvestmentType readInvestmentType(BufferedReader reader) throws IOException {
		return InvestmentType.from(investmentTypeReader.read(reader));
	}

	private InvestmentAmount readInvestmentAmount(InvestmentType investmentType, BufferedReader reader) throws
		IOException {
		return investmentAmountReaderDelegator.read(investmentType, reader);
	}

	private InvestPeriod readInvestPeriod(BufferedReader reader) throws
		IOException {
		PeriodType periodType = readPeriodType(reader);
		PeriodRange periodRange = readPeriod(reader, periodType);
		return periodType.create(periodRange);
	}

	private PeriodType readPeriodType(BufferedReader reader) throws IOException {
		return PeriodType.from(periodTypeReader.read(reader));
	}

	private PeriodRange readPeriod(BufferedReader reader, PeriodType periodType) throws IOException {
		int value = periodReader.read(reader);
		if (periodType == PeriodType.MONTH) {
			return new PeriodMonthsRange(value);
		}
		return new PeriodYearRange(value);
	}

	private InterestType readInterestType(BufferedReader reader) throws IOException {
		return InterestType.from(interestTypeReader.read(reader));
	}

	private InterestRate readInterestRatePercent(BufferedReader reader) throws IOException {
		return interestRatePercentReader.read(reader);
	}

	private Taxable readTaxable(BufferedReader reader, TaxableResolver taxableResolver) throws IOException {
		TaxType taxType = readTaxType(reader);
		TaxRate taxRate = readTaxRate(reader);
		return taxableResolver.resolve(taxType, taxRate);
	}

	private TaxType readTaxType(BufferedReader reader) throws IOException {
		return TaxType.from(taxTypeReader.read(reader));
	}

	private TaxRate readTaxRate(BufferedReader reader) throws IOException {
		return taxRateReader.read(reader);
	}

	@Override
	public InvestmentRequest readInvestmentRequest(BufferedReader reader, TaxableResolver taxableResolver) throws
		IOException {
		InvestmentType investmentType = this.readInvestmentType(reader);
		InvestmentAmount investmentAmount = this.readInvestmentAmount(investmentType, reader);
		InvestPeriod investPeriod = this.readInvestPeriod(reader);
		InterestType interestType = this.readInterestType(reader);
		InterestRate interestRate = this.readInterestRatePercent(reader);
		Taxable taxable = this.readTaxable(reader, taxableResolver);

		return new InvestmentRequest(
			investmentType,
			investmentAmount,
			investPeriod,
			interestType,
			interestRate,
			taxable
		);
	}
}
