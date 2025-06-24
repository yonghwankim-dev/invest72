package application;

import domain.invest_amount.InvestmentAmount;
import domain.invest_period.InvestPeriod;
import domain.type.InterestType;
import domain.type.InvestmentType;

public class InvestmentRequest {
	private final InvestmentType type;
	private final InvestmentAmount amount;
	private final InvestPeriod investPeriod;
	private final InterestType interestType; // 단리 or 복리
	private final int interestRatePercent; // 이자율(%)
	private final String taxType; // 일반과세, 비과세, 세금우대형
	private final double taxRatePercent; // 세금 우대형의 세율(%)

	public InvestmentRequest(
		InvestmentType type,
		InvestmentAmount amount,
		InvestPeriod investPeriod,
		InterestType interestType,
		int interestRatePercent,
		String taxType,
		double taxRatePercent
	) {
		this.type = type;
		this.amount = amount;
		this.investPeriod = investPeriod;
		this.interestType = interestType;
		this.interestRatePercent = interestRatePercent;
		this.taxType = taxType;
		this.taxRatePercent = taxRatePercent;
	}

	public InvestmentType getType() {
		return type;
	}

	public InvestmentAmount getAmount() {
		return amount;
	}

	public InvestPeriod getInvestPeriod() {
		return investPeriod;
	}

	public InterestType getInterestType() {
		return interestType;
	}

	public int getInterestRatePercent() {
		return interestRatePercent;
	}

	public String getTaxType() {
		return taxType;
	}

	public double getTaxRatePercent() {
		return taxRatePercent;
	}
}
