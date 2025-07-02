package adapter.console.reader;

import java.io.IOException;

import application.CalculateInvestmentRequest;
import application.InvestmentRequestBuilder;

public class CalculateInvestmentReaderDelegator implements InvestmentReaderDelegator {
	private final InvestReader investReader;
	private final InvestmentRequestBuilder requestBuilder;

	public CalculateInvestmentReaderDelegator(
		InvestReader investReader, InvestmentRequestBuilder requestBuilder
	) {
		this.requestBuilder = requestBuilder;
		this.investReader = investReader;
	}

	@Override
	public CalculateInvestmentRequest readInvestmentRequest() throws IOException {
		String investmentType = investReader.readInvestmentType();
		String investmentAmountLine = investReader.readInvestmentAmount();
		String periodType = investReader.readPeriodType();
		int periodValue = investReader.readPeriodValue();
		String interestType = investReader.readInterestType();
		double annualInterestRate = investReader.readAnnualInterestRate();
		String taxType = investReader.readTaxType();
		double taxRate = investReader.readTaxRate();

		CalculateInvestmentRequest.CalculateInvestmentRequestBuilder builder = requestBuilder.calculateInvestmentRequestBuilder();
		return builder.type(investmentType)
			.amount(investmentAmountLine)
			.periodType(periodType)
			.periodValue(periodValue)
			.interestType(interestType)
			.interestRate(annualInterestRate)
			.taxType(taxType)
			.taxRate(taxRate)
			.build();
	}
}
