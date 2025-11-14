package co.invest72.investment.application;

import java.time.LocalDate;

import co.invest72.investment.application.dto.CalculateGoalDto;
import co.invest72.investment.application.dto.CalculateGoalResultDto;
import co.invest72.investment.domain.InterestRate;
import co.invest72.investment.domain.InvestmentAmount;
import co.invest72.investment.domain.amount.MonthlyAmount;
import co.invest72.investment.domain.interest.AnnualInterestRate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CalculateGoalInvestment {

	public CalculateGoalResultDto calculate(CalculateGoalDto dto) {
		Integer monthlyInvestmentAmount = dto.getMonthlyInvestmentAmount();
		InvestmentAmount investmentAmount = new MonthlyAmount(monthlyInvestmentAmount);
		InterestRate interestRate = new AnnualInterestRate(dto.getAnnualInterestRate());
		Integer goalAmount = dto.getGoalAmount();
		LocalDate startDate = dto.getStartDate();

		int months = 0;
		int totalAmount = 0;
		while (totalAmount < goalAmount) {
			totalAmount += monthlyInvestmentAmount;
			totalAmount += interestRate.calMonthlyInterest(totalAmount).intValue();

			if (totalAmount >= goalAmount) {
				break;
			}
			months++;
		}

		LocalDate achievedDate = startDate.plusMonths(months);
		return new CalculateGoalResultDto(months, achievedDate);
	}
}
