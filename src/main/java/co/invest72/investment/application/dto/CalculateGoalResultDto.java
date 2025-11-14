package co.invest72.investment.application.dto;

import java.time.LocalDate;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class CalculateGoalResultDto {
	private final Integer months;
	private final LocalDate achievedDate;
}
