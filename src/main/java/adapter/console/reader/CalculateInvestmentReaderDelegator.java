package adapter.console.reader;

import java.io.BufferedReader;
import java.io.IOException;

import application.CalculateInvestmentRequest;
import application.InvestmentRequestBuilder;
import application.TaxableResolver;

public class CalculateInvestmentReaderDelegator implements InvestmentReaderDelegator {

	private final InvestmentTypeReader investmentTypeReader;
	private final InvestmentAmountReaderDelegator investmentAmountReaderDelegator;
	private final PeriodTypeReader periodTypeReader;
	private final PeriodReader periodReader;
	private final InterestTypeReader interestTypeReader;
	private final InterestRatePercentReader interestRatePercentReader;
	private final TaxTypeReader taxTypeReader;
	private final TaxRateReader taxRateReader;
	private final InvestmentRequestBuilder requestBuilder;

	public CalculateInvestmentReaderDelegator(
		InvestmentTypeReader investmentTypeReader,
		InvestmentAmountReaderDelegator investmentAmountReaderDelegator,
		PeriodTypeReader periodTypeReader,
		PeriodReader periodReader,
		InterestTypeReader interestTypeReader,
		InterestRatePercentReader interestRatePercentReader,
		TaxTypeReader taxTypeReader,
		TaxRateReader taxRateReader,
		InvestmentRequestBuilder requestBuilder
	) {
		this.investmentTypeReader = investmentTypeReader;
		this.investmentAmountReaderDelegator = investmentAmountReaderDelegator;
		this.periodTypeReader = periodTypeReader;
		this.periodReader = periodReader;
		this.interestTypeReader = interestTypeReader;
		this.interestRatePercentReader = interestRatePercentReader;
		this.taxTypeReader = taxTypeReader;
		this.taxRateReader = taxRateReader;
		this.requestBuilder = requestBuilder;
	}

	@Override
	public CalculateInvestmentRequest readInvestmentRequest(BufferedReader reader,
		TaxableResolver taxableResolver) throws IOException {
		String investmentType = investmentTypeReader.read(reader);
		String investmentAmountLine = investmentAmountReaderDelegator.read(reader);
		String periodType = periodTypeReader.read(reader);
		int periodValue = periodReader.read(reader);
		String interestType = interestTypeReader.read(reader);
		double annualInterestRate = interestRatePercentReader.read(reader);
		String taxable = taxTypeReader.read(reader);
		double taxRate = taxRateReader.read(reader);

		CalculateInvestmentRequest.CalculateInvestmentRequestBuilder builder = requestBuilder.calculateInvestmentRequestBuilder();
		return builder.type(investmentType)
			.amount(investmentAmountLine)
			.periodType(periodType)
			.periodValue(periodValue)
			.interestType(interestType)
			.interestRate(annualInterestRate)
			.taxable(taxable)
			.taxRate(taxRate)
			.build();
	}
}
