package adapter.console.reader;

import java.io.BufferedReader;
import java.io.IOException;

import application.InvestPeriodFactory;
import application.TaxableResolver;
import domain.interest_rate.InterestRate;
import domain.invest_amount.InvestmentAmount;
import domain.invest_period.InvestPeriod;
import domain.tax.TaxRate;
import domain.tax.Taxable;
import domain.type.InterestType;
import domain.type.InvestmentType;
import domain.type.PeriodType;

public class ConsoleInvestmentReaderDelegator implements InvestmentReaderDelegator {

	private final InvestmentTypeReader investmentTypeReader;
	private final InvestmentAmountReaderDelegator investmentAmountReaderDelegator;
	private final PeriodTypeReader periodTypeReader;
	private final PeriodReader periodReader;
	private final InterestTypeReader interestTypeReader;
	private final InterestRatePercentReader interestRatePercentReader;
	private final TaxTypeReader taxTypeReader;
	private final TaxRateReader taxRateReader;

	public ConsoleInvestmentReaderDelegator(InvestmentTypeReader investmentTypeReader,
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

	@Override
	public InvestmentType readInvestmentType(BufferedReader reader) throws IOException {
		return InvestmentType.from(investmentTypeReader.read(reader));
	}

	@Override
	public InvestmentAmount readInvestmentAmount(InvestmentType investmentType, BufferedReader reader) throws
		IOException {
		return investmentAmountReaderDelegator.read(investmentType, reader);
	}

	@Override
	public InvestPeriod readInvestPeriod(BufferedReader reader, InvestPeriodFactory investPeriodFactory) throws
		IOException {
		PeriodType periodType = readPeriodType(reader);
		int period = readPeriod(reader);
		return investPeriodFactory.createBy(periodType, period);
	}

	private PeriodType readPeriodType(BufferedReader reader) throws IOException {
		return periodTypeReader.read(reader);
	}

	private int readPeriod(BufferedReader reader) throws IOException {
		return periodReader.read(reader);
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
