package adapter.console.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import domain.invest_amount.InvestmentAmount;
import domain.type.InvestmentType;

public class ConsoleInvestmentAmountReaderDelegator implements InvestmentAmountReaderDelegator {

	private final List<InvestmentAmountReader> investmentAmountReaders;

	public ConsoleInvestmentAmountReaderDelegator(List<InvestmentAmountReader> investmentAmountReaders) {
		this.investmentAmountReaders = investmentAmountReaders;
	}

	@Override
	public InvestmentAmount read(InvestmentType investmentType, BufferedReader reader) throws IOException {
		for (InvestmentAmountReader investmentAmountReader : investmentAmountReaders) {
			if (investmentAmountReader.supports(investmentType)) {
				return investmentAmountReader.read(reader);
			}
		}
		throw new IllegalArgumentException("not supported investment type: " + investmentType);
	}
}
