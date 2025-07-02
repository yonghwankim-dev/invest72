package adapter.console.reader;

import java.io.BufferedReader;
import java.io.IOException;

import application.InvestmentAmountReaderRegistry;
import domain.invest_amount.InvestmentAmount;
import domain.type.InvestmentType;

public class RegistryBasedInvestmentAmountDelegator implements InvestmentAmountReaderDelegator {

	private final InvestmentAmountReaderRegistry registry;

	public RegistryBasedInvestmentAmountDelegator(InvestmentAmountReaderRegistry registry) {
		this.registry = registry;
	}

	@Override
	public InvestmentAmount read(InvestmentType investmentType, BufferedReader reader) throws IOException {
		return registry.getReaderBy(investmentType).read(reader);
	}

	@Override
	public String read(BufferedReader reader) throws IOException {
		return reader.readLine();
	}
}
