package co.invest72.investment.application.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class GoalDetailResultDto {
	private final Integer month;
	private final Integer principal;
	private final Integer interest;
	private final Integer totalProfit;

	@Builder
	public GoalDetailResultDto(Integer month, Integer principal, Integer interest, Integer totalProfit) {
		this.month = month;
		this.principal = principal;
		this.interest = interest;
		this.totalProfit = totalProfit;
	}
}
