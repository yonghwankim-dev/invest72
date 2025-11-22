package co.invest72.investment.application.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CalculateMonthlyCompoundInterestDto {
	private final Integer initialAmount; // 시작 금액
	private final Integer monthlyDeposit; // 매월 적립 금액
	private final Integer investmentYears; // 투자 기간(년)
	private final Double annualInterestRate; // 연이자율
	private final String compoundingMethod; // 복리 계산 방법 (예: monthly, quarterly, annually)

	@Override
	public String toString() {
		return "CalculateMonthlyCompoundInterestDto{" +
			"시작 금액=" + initialAmount +
			", 월 적립 금액=" + monthlyDeposit +
			", 투자 기간(년)=" + investmentYears +
			", 연이율=" + annualInterestRate +
			", 복리 방식='" + compoundingMethod + '\'' +
			'}';
	}
}
