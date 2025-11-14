package co.invest72.investment.application;

import java.time.LocalDate;

import co.invest72.investment.application.dto.CalculateGoalDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CalculateGoalInvestment {

	public int calculate(CalculateGoalDto dto) {
		Integer monthlyInvestmentAmount = dto.getMonthlyInvestmentAmount();
		Double annualInterestRate = dto.getAnnualInterestRate();
		Integer goalAmount = dto.getGoalAmount();
		LocalDate startDate = dto.getStartDate();

		int months = 0;
		int totalAmount = 0;
		while (totalAmount < goalAmount) {
			totalAmount += monthlyInvestmentAmount;
			double monthlyInterest = totalAmount * (annualInterestRate / 12);
			totalAmount += (int)monthlyInterest;
			log.info("After month {}: totalAmount = {}", months, totalAmount);
			if (totalAmount >= goalAmount) {
				break;
			}
			months++;
		}

		return months;
	}
}
