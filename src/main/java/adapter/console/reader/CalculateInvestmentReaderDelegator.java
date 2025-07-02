package adapter.console.reader;

import java.io.BufferedReader;
import java.io.IOException;

import application.CalculateInvestmentRequest;
import application.InvestmentRequestBuilder;
import application.TaxableResolver;
import domain.tax.TaxRate;
import domain.tax.Taxable;
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
		String periodType = readPeriodType(reader);
		int periodValue = readPeriodValue(reader);
		String interestType = this.readInterestType(reader);
		double annualInterestRate = this.readInterestRate(reader);
		Taxable taxable = this.readTaxable(reader, taxableResolver);

		CalculateInvestmentRequest.CalculateInvestmentRequestBuilder builder = requestBuilder.calculateInvestmentRequestBuilder();
		return builder.type(investmentType)
			.amount(investmentAmountLine)
			.periodType(periodType)
			.periodValue(periodValue)
			.interestType(interestType)
			.interestRate(annualInterestRate)
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

	private String readPeriodType(BufferedReader reader) throws IOException {
		return periodTypeReader.read(reader);
	}

	private int readPeriodValue(BufferedReader reader) throws IOException {
		return periodReader.read(reader);
	}

	private String readInterestType(BufferedReader reader) throws IOException {
		return interestTypeReader.read(reader);
	}

	private double readInterestRate(BufferedReader reader) throws IOException {
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
