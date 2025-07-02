package application;

import domain.interest_rate.InterestRate;
import domain.invest_period.InvestPeriod;
import domain.tax.Taxable;
import domain.type.InterestType;

public record CalculateInvestmentRequest(String type, String amount, InvestPeriod investPeriod,
										 InterestType interestType, InterestRate interestRate, Taxable taxable) {

	public static class CalculateInvestmentRequestBuilder {
		private String type;
		private String amount;
		private InvestPeriod investPeriod;
		private InterestType interestType;
		private InterestRate interestRate;
		private Taxable taxable;

		public CalculateInvestmentRequestBuilder type(String type) {
			this.type = type;
			return this;
		}

		public CalculateInvestmentRequestBuilder amount(String amount) {
			this.amount = amount;
			return this;
		}

		public CalculateInvestmentRequestBuilder investPeriod(InvestPeriod investPeriod) {
			this.investPeriod = investPeriod;
			return this;
		}

		public CalculateInvestmentRequestBuilder interestType(InterestType interestType) {
			this.interestType = interestType;
			return this;
		}

		public CalculateInvestmentRequestBuilder interestRate(InterestRate interestRate) {
			this.interestRate = interestRate;
			return this;
		}

		public CalculateInvestmentRequestBuilder taxable(Taxable taxable) {
			this.taxable = taxable;
			return this;
		}

		public CalculateInvestmentRequest build() {
			return new CalculateInvestmentRequest(type, amount, investPeriod, interestType, interestRate, taxable);
		}
	}
}
