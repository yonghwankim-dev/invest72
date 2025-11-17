package co.invest72.investment.application;

import java.math.BigDecimal;

import co.invest72.investment.application.dto.CalculateMonthlyCompoundInterestDto;
import co.invest72.investment.application.dto.CalculateMonthlyCompoundInterestResultDto;
import co.invest72.investment.domain.InterestRate;
import co.invest72.investment.domain.interest.AnnualInterestRate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CalculateMonthlyCompoundInterest {

	public CalculateMonthlyCompoundInterestResultDto calculate(CalculateMonthlyCompoundInterestDto dto) {
		Integer initialAmount = dto.getInitialAmount();
		Integer monthlyDeposit = dto.getMonthlyDeposit();
		Integer investmentYears = dto.getInvestmentYears();
		InterestRate interestRate = new AnnualInterestRate(dto.getAnnualInterestRate());
		int investmentMonths = investmentYears * 12;

		BigDecimal principal = BigDecimal.valueOf(initialAmount);
		BigDecimal interest = interestRate.calMonthlyInterest(principal.intValue());
		BigDecimal monthTotalProfit = principal.add(interest);

		BigDecimal totalInterest = BigDecimal.ZERO;
		for (int months = 2; months <= investmentMonths; months++) {
			principal = monthTotalProfit.add(BigDecimal.valueOf(monthlyDeposit));
			interest = interestRate.calMonthlyInterest(principal.intValue());
			monthTotalProfit = principal.add(interest);

			totalInterest = totalInterest.add(interest);
			log.info("Month: {}, Principal: {}, Interest: {}, MonthTotalProfit: {}", months, principal, interest,
				monthTotalProfit);
		}
		int totalPrincipal = calTotalPrincipal(initialAmount, monthlyDeposit, investmentYears);
		BigDecimal totalProfit = monthTotalProfit;

		return CalculateMonthlyCompoundInterestResultDto.builder()
			.totalPrincipal(totalPrincipal)
			.totalInterest(totalInterest.intValue())
			.totalProfit(totalProfit.intValue())
			.build();
	}

	private static int calTotalPrincipal(Integer initialAmount, Integer monthlyDeposit, Integer investmentYears) {
		return initialAmount + (monthlyDeposit * ((investmentYears * 12) - 1));
	}
}
