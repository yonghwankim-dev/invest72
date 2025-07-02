package application;

import domain.interest_rate.InterestRate;
import domain.tax.Taxable;

public record CalculateInvestmentRequest(String type, String amount, String periodType, int periodValue,
										 String interestType, InterestRate interestRate, Taxable taxable) {

	public static class CalculateInvestmentRequestBuilder {
		private String type;
		private String amount;
		private String periodType;
		private int periodValue;
		private String interestType;
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

		public CalculateInvestmentRequestBuilder periodType(String periodType) {
			this.periodType = periodType;
			return this;
		}

		public CalculateInvestmentRequestBuilder periodValue(int periodValue) {
			this.periodValue = periodValue;
			return this;
		}

		public CalculateInvestmentRequestBuilder interestType(String interestType) {
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
			return new CalculateInvestmentRequest(type, amount, periodType, periodValue, interestType, interestRate,
				taxable);
		}
	}
}
