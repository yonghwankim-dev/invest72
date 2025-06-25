package application;

import java.util.List;

import adapter.console.reader.InvestmentAmountReader;

public interface InvestmentAmountReaderRegistry {
	List<InvestmentAmountReader> getReaders();
}
