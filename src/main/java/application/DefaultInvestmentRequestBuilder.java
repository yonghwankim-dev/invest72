package application;

public class DefaultInvestmentRequestBuilder implements InvestmentRequestBuilder {
	@Override
	public CalculateInvestmentRequest.CalculateInvestmentRequestBuilder calculateInvestmentRequestBuilder() {
		return new CalculateInvestmentRequest.CalculateInvestmentRequestBuilder();
	}
}
