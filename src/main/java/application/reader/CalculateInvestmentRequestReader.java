package application.reader;

import java.io.BufferedReader;
import java.io.IOException;

import adapter.ui.GuidePrinter;
import domain.type.InvestmentType;

public class CalculateInvestmentRequestReader
	implements InvestmentTypeReader, PeriodTypeReader, PeriodReader, InterestTypeReader, InterestRateReader,
	TaxTypeReader, TaxRateReader, InvestmentAmountReader {

	private final BufferedReader reader;
	private final GuidePrinter guidePrinter;

	public CalculateInvestmentRequestReader(BufferedReader reader, GuidePrinter guidePrinter) {
		this.reader = reader;
		this.guidePrinter = guidePrinter;
	}

	@Override
	public String readInvestmentType() throws IOException {
		guidePrinter.printInvestmentTypeInputGuide();
		return reader.readLine();
	}

	@Override
	public String readPeriodType() throws IOException {
		guidePrinter.printPeriodTypeInputGuide();
		return reader.readLine();
	}

	@Override
	public int readPeriod() throws IOException {
		guidePrinter.printPeriodInputGuide();
		return Integer.parseInt(reader.readLine());
	}

	@Override
	public String readInterestType() throws IOException {
		guidePrinter.printInterestTypeInputGuide();
		return reader.readLine();
	}

	@Override
	public double readInterestRate() throws IOException {
		guidePrinter.printInterestPercentInputGuide();
		int percent = Integer.parseInt(reader.readLine());
		return toRate(percent);
	}

	private double toRate(double value) {
		return value / 100.0;
	}

	@Override
	public String readTaxType() throws IOException {
		guidePrinter.printTaxTypeInputGuide();
		return reader.readLine();
	}

	@Override
	public double readTaxRate() throws IOException {
		guidePrinter.printTaxRateInputGuide();
		return toRate(Double.parseDouble(reader.readLine()));
	}

	@Override
	public String readAmount() throws IOException {
		return reader.readLine();
	}

	@Override
	public boolean supports(InvestmentType investmentType) {
		return false;
	}
}
