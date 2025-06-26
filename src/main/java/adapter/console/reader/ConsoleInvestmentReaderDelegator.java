package adapter.console.reader;

import java.io.BufferedReader;
import java.io.IOException;

import domain.interest_rate.InterestRate;
import domain.invest_amount.InvestmentAmount;
import domain.tax.TaxRate;
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
		return investmentTypeReaderDelegator.read(reader);
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
	public String readInterestType(BufferedReader reader) throws IOException {
		return interestTypeReader.read(reader);
	}

	@Override
	public InterestRate readInterestRatePercent(BufferedReader reader) throws IOException {
		return interestRatePercentReader.read(reader);
	}

	@Override
	public String readTaxType(BufferedReader reader) throws IOException {
		return taxTypeReader.read(reader);
	}

	@Override
	public TaxRate readTaxRate(BufferedReader reader) throws IOException {
		return taxRateReader.read(reader);
	}
}
