package co.invest72.investment.application;

import java.math.BigDecimal;

import co.invest72.investment.application.dto.CalculateMonthlyCompoundInterestDto;
import co.invest72.investment.application.dto.CalculateMonthlyCompoundInterestResultDto;
import co.invest72.investment.domain.InterestRate;
import co.invest72.investment.domain.InvestPeriod;
import co.invest72.investment.domain.InvestmentAmount;
import co.invest72.investment.domain.amount.MonthlyAmount;
import co.invest72.investment.domain.interest.AnnualInterestRate;
import co.invest72.investment.domain.period.YearlyInvestPeriod;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CalculateMonthlyCompoundInterest {

	public CalculateMonthlyCompoundInterestResultDto calculate(CalculateMonthlyCompoundInterestDto dto) {
		Integer initialAmount = dto.getInitialAmount();
		InvestmentAmount investmentAmount = new MonthlyAmount(dto.getMonthlyDeposit());
		InvestPeriod investPeriod = new YearlyInvestPeriod(dto.getInvestmentYears());
		InterestRate interestRate = new AnnualInterestRate(dto.getAnnualInterestRate());

		BigDecimal principal = BigDecimal.valueOf(initialAmount);
		BigDecimal interest = interestRate.calMonthlyInterest(principal.intValue());
		BigDecimal monthTotalProfit = principal.add(interest);

		BigDecimal totalInterest = BigDecimal.ZERO;
		for (int months = 2; months <= investPeriod.getMonths(); months++) {
			principal = monthTotalProfit.add(investmentAmount.getAmount());
			interest = interestRate.calMonthlyInterest(principal.intValue());
			monthTotalProfit = principal.add(interest);

			totalInterest = totalInterest.add(interest);
			log.info("Month: {}, Principal: {}, Interest: {}, MonthTotalProfit: {}", months, principal, interest,
				monthTotalProfit);
		}
		int totalPrincipal = calTotalPrincipal(initialAmount, investmentAmount.getAmount().intValue(), investPeriod);
		BigDecimal totalProfit = monthTotalProfit;

		return CalculateMonthlyCompoundInterestResultDto.builder()
			.totalPrincipal(totalPrincipal)
			.totalInterest(totalInterest.intValue())
			.totalProfit(totalProfit.intValue())
			.build();
	}

	private static int calTotalPrincipal(Integer initialAmount, Integer monthlyDeposit, InvestPeriod investPeriod) {
		return initialAmount + (monthlyDeposit * (investPeriod.getMonths() - 1));
	}
}
