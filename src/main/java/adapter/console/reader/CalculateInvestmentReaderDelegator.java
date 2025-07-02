package adapter.console.reader;

import java.io.BufferedReader;
import java.io.IOException;

import application.CalculateInvestmentRequest;
import application.InvestmentRequestBuilder;
import application.TaxableResolver;
import domain.interest_rate.InterestRate;
import domain.invest_period.InvestPeriod;
import domain.invest_period.PeriodMonthsRange;
import domain.invest_period.PeriodRange;
import domain.invest_period.PeriodYearRange;
import domain.tax.TaxRate;
import domain.tax.Taxable;
import domain.type.InterestType;
import domain.type.PeriodType;
import domain.type.TaxType;

public class CalculateInvestmentReaderDelegator implements InvestmentReaderDelegator {

	private final InvestmentTypeReader investmentTypeReader;
	private final InvestmentAmountReaderDelegator investmentAmountReaderDelegator;
	private final PeriodTypeReader periodTypeReader;
	private final PeriodReader periodReader;
	private final InterestTypeReader interestTypeReader;
	private final InterestRatePercentReader interestRatePercentReader;
	private final TaxTypeReader taxTypeReader;
	private final TaxRateReader taxRateReader;
	private final InvestmentRequestBuilder requestBuilder;

	public CalculateInvestmentReaderDelegator(
		InvestmentTypeReader investmentTypeReader,
		InvestmentAmountReaderDelegator investmentAmountReaderDelegator,
		PeriodTypeReader periodTypeReader,
		PeriodReader periodReader,
		InterestTypeReader interestTypeReader,
		InterestRatePercentReader interestRatePercentReader,
		TaxTypeReader taxTypeReader,
		TaxRateReader taxRateReader,
		InvestmentRequestBuilder requestBuilder
	) {
		this.investmentTypeReader = investmentTypeReader;
		this.investmentAmountReaderDelegator = investmentAmountReaderDelegator;
		this.periodTypeReader = periodTypeReader;
		this.periodReader = periodReader;
		this.interestTypeReader = interestTypeReader;
		this.interestRatePercentReader = interestRatePercentReader;
		this.taxTypeReader = taxTypeReader;
		this.taxRateReader = taxRateReader;
		this.requestBuilder = requestBuilder;
	}

	@Override
	public CalculateInvestmentRequest readInvestmentRequest(BufferedReader reader,
		TaxableResolver taxableResolver) throws IOException {
		String investmentType = this.readInvestmentType(reader);
		String investmentAmountLine = this.readInvestmentAmount(reader);
		InvestPeriod investPeriod = this.readInvestPeriod(reader);
		InterestType interestType = this.readInterestType(reader);
		InterestRate interestRate = this.readInterestRatePercent(reader);
		Taxable taxable = this.readTaxable(reader, taxableResolver);

		CalculateInvestmentRequest.CalculateInvestmentRequestBuilder builder = requestBuilder.calculateInvestmentRequestBuilder();
		return builder.type(investmentType)
			.amount(investmentAmountLine)
			.investPeriod(investPeriod)
			.interestType(interestType)
			.interestRate(interestRate)
			.taxable(taxable)
			.build();
	}

	private String readInvestmentType(BufferedReader reader) throws IOException {
		return investmentTypeReader.read(reader);
	}

	private String readInvestmentAmount(BufferedReader reader) throws
		IOException {
		return investmentAmountReaderDelegator.read(reader);
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
}
