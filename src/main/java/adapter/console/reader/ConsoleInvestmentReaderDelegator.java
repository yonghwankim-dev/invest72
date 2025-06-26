package adapter.console.reader;

import java.io.BufferedReader;
import java.io.IOException;

import application.TaxableResolver;
import domain.interest_rate.InterestRate;
import domain.invest_amount.InvestmentAmount;
import domain.tax.TaxRate;
import domain.tax.Taxable;
import domain.type.InterestType;
import domain.type.InvestmentType;
import domain.type.PeriodType;

public class ConsoleInvestmentReaderDelegator implements InvestmentReaderDelegator {

	private final InvestmentTypeReaderDelegator investmentTypeReaderDelegator;
	private final InvestmentAmountReaderDelegator investmentAmountReaderDelegator;
	private final PeriodTypeReaderDelegator periodTypeReaderDelegator;
	private final PeriodReaderDelegator periodReaderDelegator;
	private final InterestTypeReader interestTypeReader;
	private final InterestRatePercentReader interestRatePercentReader;
	private final TaxTypeReader taxTypeReader;
	private final TaxRateReader taxRateReader;

	public ConsoleInvestmentReaderDelegator(InvestmentTypeReaderDelegator investmentTypeReaderDelegator,
		InvestmentAmountReaderDelegator investmentAmountReaderDelegator,
		PeriodTypeReaderDelegator periodTypeReaderDelegator, PeriodReaderDelegator periodReaderDelegator,
		InterestTypeReader interestTypeReader, InterestRatePercentReader interestRatePercentReader,
		TaxTypeReader taxTypeReader, TaxRateReader taxRateReader) {
		this.investmentTypeReaderDelegator = investmentTypeReaderDelegator;
		this.investmentAmountReaderDelegator = investmentAmountReaderDelegator;
		this.periodTypeReaderDelegator = periodTypeReaderDelegator;
		this.periodReaderDelegator = periodReaderDelegator;
		this.interestTypeReader = interestTypeReader;
		this.interestRatePercentReader = interestRatePercentReader;
		this.taxTypeReader = taxTypeReader;
		this.taxRateReader = taxRateReader;
	}

	@Override
	public InvestmentType readInvestmentType(BufferedReader reader) throws IOException {
		return InvestmentType.from(investmentTypeReaderDelegator.read(reader));
	}

	@Override
	public InvestmentAmount readInvestmentAmount(InvestmentType investmentType, BufferedReader reader) throws
		IOException {
		return investmentAmountReaderDelegator.read(investmentType, reader);
	}

	@Override
	public PeriodType readPeriodType(BufferedReader reader) throws IOException {
		return periodTypeReaderDelegator.read(reader);
	}

	@Override
	public int readPeriod(BufferedReader reader) throws IOException {
		return periodReaderDelegator.read(reader);
	}

	@Override
	public InterestType readInterestType(BufferedReader reader) throws IOException {
		return InterestType.from(interestTypeReader.read(reader));
	}

	@Override
	public InterestRate readInterestRatePercent(BufferedReader reader) throws IOException {
		return interestRatePercentReader.read(reader);
	}

	@Override
	public Taxable readTaxable(BufferedReader reader, TaxableResolver taxableResolver) throws IOException {
		String taxType = readTaxType(reader);
		TaxRate taxRate = readTaxRate(reader);
		return taxableResolver.resolve(taxType, taxRate);
	}

	private String readTaxType(BufferedReader reader) throws IOException {
		return taxTypeReader.read(reader);
	}

	private TaxRate readTaxRate(BufferedReader reader) throws IOException {
		return taxRateReader.read(reader);
	}
}
