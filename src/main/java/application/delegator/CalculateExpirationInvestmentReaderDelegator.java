package application.delegator;

import java.io.IOException;

import application.reader.CalculateInvestmentRequestReader;
import application.registry.InvestmentAmountReaderStrategyRegistry;
import co.invest72.investment.application.dto.CalculateInvestmentRequest;
import co.invest72.investment.domain.investment.InvestmentType;

public class CalculateExpirationInvestmentReaderDelegator
	implements InvestmentReaderDelegator<CalculateInvestmentRequest> {
	private final InvestmentAmountReaderStrategyRegistry amountReaderStrategyRegistry;
	private final CalculateInvestmentRequestReader reader;

	public CalculateExpirationInvestmentReaderDelegator(
		InvestmentAmountReaderStrategyRegistry amountReaderStrategyRegistry,
		CalculateInvestmentRequestReader reader) {
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

		return CalculateInvestmentRequest.builder()
			.type(investmentType)
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
		return amountReaderStrategyRegistry.getStrategy(type).readAmount(reader);
	}
}
