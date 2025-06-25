package adapter.console.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import application.InvestmentAmountReaderRegistry;
import domain.invest_amount.InvestmentAmount;
import domain.type.InvestmentType;

public class ConsoleInvestmentAmountReaderDelegator implements InvestmentAmountReaderDelegator {

	private final List<InvestmentAmountReader> investmentAmountReaders;
	private final InvestmentAmountReaderRegistry registry;

	public ConsoleInvestmentAmountReaderDelegator(List<InvestmentAmountReader> investmentAmountReaders,
		InvestmentAmountReaderRegistry registry) {
		this.investmentAmountReaders = investmentAmountReaders;
		this.registry = registry;
	}

	@Override
	public InvestmentAmount read(InvestmentType investmentType, BufferedReader reader) throws IOException {
		return registry.getReaderBy(investmentType)
			.read(reader);
	}
}
