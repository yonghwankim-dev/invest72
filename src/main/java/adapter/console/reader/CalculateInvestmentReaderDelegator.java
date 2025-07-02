package adapter.console.reader;

import java.io.IOException;

import application.CalculateInvestmentRequest;
import application.InvestmentRequestBuilder;
import application.TaxableResolver;

public class CalculateInvestmentReaderDelegator implements InvestmentReaderDelegator {
	private final InvestmentRequestBuilder requestBuilder;
	private final InvestReader investReader;

	public CalculateInvestmentReaderDelegator(
		InvestmentRequestBuilder requestBuilder,
		InvestReader investReader
	) {
		this.requestBuilder = requestBuilder;
		this.investReader = investReader;
	}

	@Override
	public CalculateInvestmentRequest readInvestmentRequest(TaxableResolver taxableResolver) throws IOException {
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
