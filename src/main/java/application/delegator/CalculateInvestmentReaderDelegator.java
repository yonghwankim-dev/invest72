package application.delegator;

import java.io.IOException;

import application.builder.InvestmentRequestBuilder;
import application.reader.CalculateInvestmentRequestReader;
import application.reader.InvestmentAmountReader;
import application.registry.InvestmentAmountReaderStrategyRegistry;
import application.request.CalculateInvestmentRequest;
import domain.type.InvestmentType;

public class CalculateInvestmentReaderDelegator implements InvestmentReaderDelegator<CalculateInvestmentRequest> {
	private final InvestmentRequestBuilder requestBuilder;
	private final InvestmentAmountReaderStrategyRegistry amountReaderStrategyRegistry;
	private final CalculateInvestmentRequestReader reader;

	public CalculateInvestmentReaderDelegator(InvestmentRequestBuilder requestBuilder,
		InvestmentAmountReaderStrategyRegistry amountReaderStrategyRegistry,
		CalculateInvestmentRequestReader reader) {
		this.requestBuilder = requestBuilder;
		this.amountReaderStrategyRegistry = amountReaderStrategyRegistry;
		this.reader = reader;
	}

	@Override
	public CalculateInvestmentRequest readInvestmentRequest() throws IOException {
		String investmentType = reader.readInvestmentType();
		String investmentAmount = readInvestmentAmount(investmentType);
		String periodType = reader.readPeriodType();
		int periodValue = reader.readPeriod();
		String interestType = reader.readInterestType();
		double annualInterestRate = reader.readInterestRate();
		String taxType = reader.readTaxType();
		double taxRate = reader.readTaxRate();

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
		if (reader instanceof InvestmentAmountReader amountReader) {
			return amountReaderStrategyRegistry.getStrategy(type).readAmount(amountReader);
		}
		throw new IllegalArgumentException("지원하지 않는 Reader 타입입니다. " + reader);
	}
}
