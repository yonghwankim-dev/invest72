package co.invest72.investment.presentation.request;

import java.time.LocalDate;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CalculateGoalRequest {
	private Integer monthlyInvestmentAmount;
	private Double annualInterestRate;
	private Integer goalAmount;
	private LocalDate startDate;

	@Builder
	public CalculateGoalRequest(Integer monthlyInvestmentAmount, Double annualInterestRate, Integer goalAmount,
		LocalDate startDate) {
		this.monthlyInvestmentAmount = monthlyInvestmentAmount;
		this.annualInterestRate = annualInterestRate;
		this.goalAmount = goalAmount;
		this.startDate = startDate;
	}
}
