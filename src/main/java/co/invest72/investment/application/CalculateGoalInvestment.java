package co.invest72.investment.application;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import co.invest72.investment.application.dto.CalculateGoalDto;
import co.invest72.investment.application.dto.CalculateGoalResultDto;
import co.invest72.investment.application.dto.GoalDetailResultDto;
import co.invest72.investment.domain.InterestRate;
import co.invest72.investment.domain.InvestmentAmount;
import co.invest72.investment.domain.amount.MonthlyAmount;
import co.invest72.investment.domain.interest.AnnualInterestRate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CalculateGoalInvestment {

	public CalculateGoalResultDto calculate(CalculateGoalDto dto) {
		InvestmentAmount investmentAmount = new MonthlyAmount(dto.getMonthlyInvestmentAmount());
		InterestRate interestRate = new AnnualInterestRate(dto.getAnnualInterestRate());
		BigDecimal goalAmount = BigDecimal.valueOf(dto.getGoalAmount());

		int months = 0;
		BigDecimal totalAmount = BigDecimal.ZERO;
		while (!hasReachedGoal(totalAmount, goalAmount)) {
			totalAmount = investmentAmount.addAmount(totalAmount);
			totalAmount = interestRate.calMonthlyInterest(totalAmount.intValue())
				.add(totalAmount);

			if (hasReachedGoal(totalAmount, goalAmount)) {
				break;
			}
			months++;
		}

		LocalDate startDate = dto.getStartDate();
		LocalDate achievedDate = startDate.plusMonths(months);
		return new CalculateGoalResultDto(months, achievedDate);
	}

	private boolean hasReachedGoal(BigDecimal totalAmount, BigDecimal goalAmount) {
		return totalAmount.compareTo(goalAmount) >= 0;
	}

	public List<GoalDetailResultDto> calculateMonthlyDetails(CalculateGoalDto dto) {
		List<GoalDetailResultDto> result = new ArrayList<>();
		InvestmentAmount investmentAmount = new MonthlyAmount(dto.getMonthlyInvestmentAmount());
		InterestRate interestRate = new AnnualInterestRate(dto.getAnnualInterestRate());
		BigDecimal goalAmount = BigDecimal.valueOf(dto.getGoalAmount());

		int month = 1;
		BigDecimal totalAmount = BigDecimal.ZERO;
		BigDecimal principal = BigDecimal.ZERO;
		BigDecimal interest = BigDecimal.ZERO;
		while (!hasReachedGoal(totalAmount, goalAmount)) {
			totalAmount = investmentAmount.addAmount(totalAmount);
			principal = principal.add(BigDecimal.valueOf(dto.getMonthlyInvestmentAmount()));
			// TODO: 4166 (x) 4167(o) 되도록 수정
			interest = interest.add(interestRate.calMonthlyInterest(totalAmount.intValue()));
			totalAmount = totalAmount.add(interest);
			BigDecimal totalProfit = principal.add(interest);

			result.add(
				new GoalDetailResultDto(month, principal.intValue(), interest.intValue(), totalProfit.intValue()));
			if (hasReachedGoal(totalAmount, goalAmount)) {
				break;
			}

			month++;
		}

		return result;
	}
}
