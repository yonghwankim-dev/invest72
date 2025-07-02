package application;

public record CalculateInvestmentRequest(String type, String amount, String periodType, int periodValue,
										 String interestType, double annualInterestRate, String taxable,
										 double taxRate) {
	public static class CalculateInvestmentRequestBuilder {
		private String type;
		private String amount;
		private String periodType;
		private int periodValue;
		private String interestType;
		private double annualInterestRate;
		private String taxable;
		private double taxRate;

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

		public CalculateInvestmentRequestBuilder interestRate(double annualInterestRate) {
			this.annualInterestRate = annualInterestRate;
			return this;
		}

		public CalculateInvestmentRequestBuilder taxable(String taxable) {
			this.taxable = taxable;
			return this;
		}

		public CalculateInvestmentRequestBuilder taxRate(double taxRate) {
			this.taxRate = taxRate;
			return this;
		}

		public CalculateInvestmentRequest build() {
			return new CalculateInvestmentRequest(type, amount, periodType, periodValue, interestType,
				annualInterestRate, taxable, taxRate);
		}
	}
}
