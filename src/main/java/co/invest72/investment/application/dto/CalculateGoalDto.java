package co.invest72.investment.application.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CalculateGoalDto {
	private Integer initialAmount; // 시작 금액
	private Integer monthlyDeposit; // 매월 적립 금액
	private Integer investmentYears; // 투자 기간(년)
	private Double annualInterestRate; // 연이자율
	private String compoundingMethod; // 복리 계산 방법 (예: monthly, quarterly, annually)

	@Override
	public String toString() {
		return "CalculateGoalDto{" +
			"시작 금액=" + initialAmount +
			", 월 적립 금액=" + monthlyDeposit +
			", 투자 기간(년)=" + investmentYears +
			", 연이율=" + annualInterestRate +
			", 복리 방식='" + compoundingMethod + '\'' +
			'}';
	}
}
