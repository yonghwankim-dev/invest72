package co.invest72.investment.application.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class CalculateMonthlyCompoundInterestResultDto {
	private final Integer totalPrincipal;
	private final Integer totalInterest;
	private final Integer totalProfit;
}
