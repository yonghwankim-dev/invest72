package application;


public class InvestmentRequest {
	private String type; // 예금 or 적금
	private int amount; // 예치금액 or 월납입액
	private String periodType; // 적립기간 종류: 월 or 년
	private int period; // 적립기간 값
	private String interestType; // 단리 or 복리
	private int interestRatePercent; // 이자율(%)
	private String taxType; // 일반과세, 비과세, 세금우대형
	private double taxRatePercent; // 세금 우대형의 세율(%)

	public InvestmentRequest(String type, int amount, String periodType, int period, String interestType,
		int interestRatePercent, String taxType, double taxRatePercent) {
		this.type = type;
		this.amount = amount;
		this.periodType = periodType;
		this.period = period;
		this.interestType = interestType;
		this.interestRatePercent = interestRatePercent;
		this.taxType = taxType;
		this.taxRatePercent = taxRatePercent;
	}

	public String getType() {
		return type;
	}

	public int getAmount() {
		return amount;
	}

	public String getPeriodType() {
		return periodType;
	}

	public int getPeriod() {
		return period;
	}

	public String getInterestType() {
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
