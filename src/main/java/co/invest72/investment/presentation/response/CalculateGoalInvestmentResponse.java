package co.invest72.investment.presentation.response;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;

@Getter
public class CalculateGoalInvestmentResponse {
	private final Integer months;
	private final LocalDate achievedDate;
	private final List<Object> details;

	public CalculateGoalInvestmentResponse(Integer months, LocalDate achievedDate, List<Object> details) {
		this.months = months;
		this.achievedDate = achievedDate;
		this.details = details;
	}
}
