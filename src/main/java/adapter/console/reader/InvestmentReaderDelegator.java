package adapter.console.reader;

import java.io.BufferedReader;
import java.io.IOException;

import application.TaxableResolver;
import domain.interest_rate.InterestRate;
import domain.invest_amount.InvestmentAmount;
import domain.invest_period.InvestPeriod;
import domain.tax.Taxable;
import domain.type.InterestType;
import domain.type.InvestmentType;

public interface InvestmentReaderDelegator {
	InvestmentType readInvestmentType(BufferedReader reader) throws IOException;

	InvestmentAmount readInvestmentAmount(InvestmentType investmentType, BufferedReader reader) throws IOException;

	InvestPeriod readInvestPeriod(BufferedReader reader) throws IOException;

	InterestType readInterestType(BufferedReader reader) throws IOException;

	InterestRate readInterestRatePercent(BufferedReader reader) throws IOException;

	Taxable readTaxable(BufferedReader reader, TaxableResolver taxableResolver) throws IOException;
}
