package adapter.console.reader;

import java.io.IOException;
import java.util.Map;

import application.CalculateInvestmentRequest;
import application.InvestmentRequestBuilder;
import domain.type.InvestmentType;

public class CalculateInvestmentReaderDelegator implements InvestmentReaderDelegator {
	private final InvestReader investReader;
	private final InvestmentRequestBuilder requestBuilder;

	private final Map<InvestmentType, InvestmentAmountReaderStrategy> amountReaderStrategies;

	public CalculateInvestmentReaderDelegator(InvestReader investReader, InvestmentRequestBuilder requestBuilder,
		Map<InvestmentType, InvestmentAmountReaderStrategy> amountReaderStrategies
	) {
		this.requestBuilder = requestBuilder;
		this.investReader = investReader;
		this.amountReaderStrategies = amountReaderStrategies;
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
		InvestmentAmountReaderStrategy strategy = amountReaderStrategies.get(type);
		if (strategy == null) {
			throw new IllegalArgumentException("지원하지 않는 투자 유형입니다: " + investmentType);
		}
		return strategy.readAmount(investReader);
	}
}
