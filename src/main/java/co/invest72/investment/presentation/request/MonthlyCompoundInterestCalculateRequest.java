package co.invest72.investment.presentation.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 적립식 복리 투자에 대한 요청 클래스
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MonthlyCompoundInterestCalculateRequest {
	private Integer initialAmount; // 시작 금액
	private Integer monthlyDeposit; // 매월 적립 금액
	private Integer investmentYears; // 투자 기간(년)
	private Double annualInterestRate; // 연이자율
	private String compoundingMethod; // 복리 계산 방법 (예: monthly, quarterly, annually)

	@Builder
	public MonthlyCompoundInterestCalculateRequest(Integer initialAmount, Integer monthlyDeposit,
		Integer investmentYears,
		Double annualInterestRate, String compoundingMethod) {
		this.initialAmount = initialAmount;
		this.monthlyDeposit = monthlyDeposit;
		this.investmentYears = investmentYears;
		this.annualInterestRate = annualInterestRate;
		this.compoundingMethod = compoundingMethod;
	}
}
