package co.invest72.product.domain;

import co.invest72.investment.domain.interest.InterestType;
import co.invest72.investment.domain.investment.InvestmentType;
import co.invest72.investment.domain.tax.TaxType;

public class InvestmentProductEntity {
	private Long id;
	private String uid;
	private InvestmentType investmentType;
	private int investmentAmount;
	private InterestType interestType;
	private double annualRate;
	private int investmentPeriodMonth;
	private TaxType taxType;
	private double taxRate;

	public static InvestmentProductEntityBuilder builder() {
		return new InvestmentProductEntityBuilder();
	}

	public InvestmentProductEntity(InvestmentProductEntityBuilder builder) {
		this.id = builder.id;
		this.uid = builder.uid;
		this.investmentType = builder.investmentType;
		this.investmentAmount = builder.investmentAmount;
		this.interestType = builder.interestType;
		this.annualRate = builder.annualRate;
		this.investmentPeriodMonth = builder.investmentPeriodMonth;
		this.taxType = builder.taxType;
		this.taxRate = builder.taxRate;
	}

	public Long getId() {
		return id;
	}

	public String getUid() {
		return uid;
	}

	public InvestmentType getInvestmentType() {
		return investmentType;
	}

	public int getInvestmentAmount() {
		return investmentAmount;
	}

	public InterestType getInterestType() {
		return interestType;
	}

	public double getAnnualRate() {
		return annualRate;
	}

	public int getInvestmentPeriodMonth() {
		return investmentPeriodMonth;
	}

	public TaxType getTaxType() {
		return taxType;
	}

	public double getTaxRate() {
		return taxRate;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public static class InvestmentProductEntityBuilder {
		private Long id;
		private String uid;
		private InvestmentType investmentType;
		private int investmentAmount;
		private InterestType interestType;
		private double annualRate;
		private int investmentPeriodMonth;
		private TaxType taxType;
		private double taxRate;

		public InvestmentProductEntityBuilder id(Long id) {
			this.id = id;
			return this;
		}

		public InvestmentProductEntityBuilder uid(String uid) {
			this.uid = uid;
			return this;
		}

		public InvestmentProductEntityBuilder investmentType(InvestmentType investmentType) {
			this.investmentType = investmentType;
			return this;
		}

		public InvestmentProductEntityBuilder investmentAmount(int investmentAmount) {
			this.investmentAmount = investmentAmount;
			return this;
		}

		public InvestmentProductEntityBuilder interestType(InterestType interestType) {
			this.interestType = interestType;
			return this;
		}

		public InvestmentProductEntityBuilder annualRate(double annualRate) {
			this.annualRate = annualRate;
			return this;
		}

		public InvestmentProductEntityBuilder investmentPeriodMonth(int investmentPeriodMonth) {
			this.investmentPeriodMonth = investmentPeriodMonth;
			return this;
		}

		public InvestmentProductEntityBuilder taxType(TaxType taxType) {
			this.taxType = taxType;
			return this;
		}

		public InvestmentProductEntityBuilder taxRate(double taxRate) {
			this.taxRate = taxRate;
			return this;
		}

		public InvestmentProductEntity build() {
			return new InvestmentProductEntity(this);
		}
	}
}
