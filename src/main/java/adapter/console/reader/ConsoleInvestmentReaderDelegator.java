package adapter.console.reader;

import java.io.BufferedReader;
import java.io.IOException;

import domain.invest_amount.InvestmentAmount;
import domain.type.InvestmentType;
import domain.type.PeriodType;

public class ConsoleInvestmentReaderDelegator implements InvestmentReaderDelegator {

	private final InvestmentTypeReaderDelegator investmentTypeReaderDelegator;
	private final InvestmentAmountReaderDelegator investmentAmountReaderDelegator;
	private final PeriodTypeReaderDelegator periodTypeReaderDelegator;
	private final PeriodReaderDelegator periodReaderDelegator;

	public ConsoleInvestmentReaderDelegator(InvestmentTypeReaderDelegator investmentTypeReaderDelegator,
		InvestmentAmountReaderDelegator investmentAmountReaderDelegator,
		PeriodTypeReaderDelegator periodTypeReaderDelegator, PeriodReaderDelegator periodReaderDelegator) {
		this.investmentTypeReaderDelegator = investmentTypeReaderDelegator;
		this.investmentAmountReaderDelegator = investmentAmountReaderDelegator;
		this.periodTypeReaderDelegator = periodTypeReaderDelegator;
		this.periodReaderDelegator = periodReaderDelegator;
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
		return null;
	}
}
