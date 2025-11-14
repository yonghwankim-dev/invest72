package co.invest72.investment.application.dto;

import java.time.LocalDate;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class CalculateGoalDto {
	private final Integer monthlyInvestmentAmount;
	private final Double annualInterestRate;
	private final Integer goalAmount;
	private final LocalDate startDate;

	@Override
	public String toString() {
		return "CalculateGoalDto{" +
			"monthlyInvestmentAmount=" + monthlyInvestmentAmount +
			", annualInterestRate=" + annualInterestRate +
			", goalAmount=" + goalAmount +
			", startDate=" + startDate +
			'}';
	}
}
