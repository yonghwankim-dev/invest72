package application;

import domain.interest_rate.InterestRate;
import domain.invest_amount.InvestmentAmount;
import domain.invest_period.InvestPeriod;
import domain.tax.Taxable;
import domain.type.InterestType;
import domain.type.InvestmentType;

public record CalculateInvestmentRequest(InvestmentType type, InvestmentAmount amount, InvestPeriod investPeriod,
										 InterestType interestType, InterestRate interestRate, Taxable taxable) {

	public static class CalculateInvestmentRequestBuilder {
		private InvestmentType type;
		private InvestmentAmount amount;
		private InvestPeriod investPeriod;
		private InterestType interestType;
		private InterestRate interestRate;
		private Taxable taxable;

		public CalculateInvestmentRequestBuilder type(InvestmentType type) {
			this.type = type;
			return this;
		}

		public CalculateInvestmentRequestBuilder amount(InvestmentAmount amount) {
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
