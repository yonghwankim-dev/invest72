package application.delegator;

import java.io.IOException;

import application.builder.InvestmentRequestBuilder;
import application.reader.InvestReader;
import application.registry.InvestmentAmountReaderStrategyRegistry;
import application.request.CalculateInvestmentRequest;
import domain.type.InvestmentType;

public class CalculateInvestmentReaderDelegator implements InvestmentReaderDelegator<CalculateInvestmentRequest> {
	private final InvestReader investReader;
	private final InvestmentRequestBuilder requestBuilder;
	private final InvestmentAmountReaderStrategyRegistry amountReaderStrategyRegistry;

	public CalculateInvestmentReaderDelegator(InvestReader investReader, InvestmentRequestBuilder requestBuilder,
		InvestmentAmountReaderStrategyRegistry amountReaderStrategyRegistry
	) {
		this.requestBuilder = requestBuilder;
		this.investReader = investReader;
		this.amountReaderStrategyRegistry = amountReaderStrategyRegistry;
	}

	@Override
	public CalculateInvestmentRequest readInvestmentRequest() throws IOException {
		String investmentType = investReader.readInvestmentType();
		String investmentAmount = readInvestmentAmount(investmentType);
		String periodType = investReader.readPeriodType();
		int periodValue = investReader.readPeriodValue();
		String interestType = investReader.readInterestType();
		double annualInterestRate = investReader.readAnnualInterestRate();
		String taxType = investReader.readTaxType();
		double taxRate = investReader.readTaxRate();

		CalculateInvestmentRequest.CalculateInvestmentRequestBuilder builder = requestBuilder.calculateInvestmentRequestBuilder();
		return builder.type(investmentType)
			.amount(investmentAmount)
			.periodType(periodType)
			.periodValue(periodValue)
			.interestType(interestType)
			.interestRate(annualInterestRate)
			.taxType(taxType)
			.taxRate(taxRate)
			.build();
	}

	private String readInvestmentAmount(String investmentType) throws IOException {
		InvestmentType type = InvestmentType.from(investmentType);
		return amountReaderStrategyRegistry.getStrategy(type).readAmount(investReader);
	}
}
