package co.invest72.investment.presentation.request;

import java.util.Objects;

public final class CalculateInvestmentRequest {
	private final String type;
	private final String amount;
	private final String periodType;
	private final int periodValue;
	private final String interestType;
	private final double annualInterestRate;
	private final String taxType;
	private final double taxRate;

	public CalculateInvestmentRequest(String type, String amount, String periodType, int periodValue,
		String interestType, double annualInterestRate, String taxType,
		double taxRate) {
		this.type = type;
		this.amount = amount;
		this.periodType = periodType;
		this.periodValue = periodValue;
		this.interestType = interestType;
		this.annualInterestRate = annualInterestRate;
		this.taxType = taxType;
		this.taxRate = taxRate;
	}

	public CalculateInvestmentRequest(CalculateInvestmentRequestBuilder builder) {
		this(builder.type, builder.amount, builder.periodType, builder.periodValue,
			builder.interestType, builder.annualInterestRate, builder.taxType, builder.taxRate);
	}

	public static CalculateInvestmentRequestBuilder builder() {
		return new CalculateInvestmentRequestBuilder();
	}

	public String type() {
		return type;
	}

	public String amount() {
		return amount;
	}

	public String periodType() {
		return periodType;
	}

	public int periodValue() {
		return periodValue;
	}

	public String interestType() {
		return interestType;
	}

	public double annualInterestRate() {
		return annualInterestRate;
	}

	public String taxType() {
		return taxType;
	}

	public double taxRate() {
		return taxRate;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj == null || obj.getClass() != this.getClass())
			return false;
		var that = (CalculateInvestmentRequest)obj;
		return Objects.equals(this.type, that.type) &&
			Objects.equals(this.amount, that.amount) &&
			Objects.equals(this.periodType, that.periodType) &&
			this.periodValue == that.periodValue &&
			Objects.equals(this.interestType, that.interestType) &&
			Double.doubleToLongBits(this.annualInterestRate) == Double.doubleToLongBits(that.annualInterestRate) &&
			Objects.equals(this.taxType, that.taxType) &&
			Double.doubleToLongBits(this.taxRate) == Double.doubleToLongBits(that.taxRate);
	}

	@Override
	public int hashCode() {
		return Objects.hash(type, amount, periodType, periodValue, interestType, annualInterestRate, taxType, taxRate);
	}

	@Override
	public String toString() {
		return "CalculateInvestmentRequest[" +
			"type=" + type + ", " +
			"amount=" + amount + ", " +
			"periodType=" + periodType + ", " +
			"periodValue=" + periodValue + ", " +
			"interestType=" + interestType + ", " +
			"annualInterestRate=" + annualInterestRate + ", " +
			"taxType=" + taxType + ", " +
			"taxRate=" + taxRate + ']';
	}

	public static class CalculateInvestmentRequestBuilder {
		private String type;
		private String amount;
		private String periodType;
		private int periodValue;
		private String interestType;
		private double annualInterestRate;
		private String taxType;
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

		public CalculateInvestmentRequestBuilder taxType(String taxType) {
			this.taxType = taxType;
			return this;
		}

		public CalculateInvestmentRequestBuilder taxRate(double taxRate) {
			this.taxRate = taxRate;
			return this;
		}

		public CalculateInvestmentRequest build() {
			return new CalculateInvestmentRequest(this);
		}
	}
}
