package co.invest72.investment.application;

import java.util.ArrayList;

import co.invest72.investment.application.dto.CalculateMonthlyCompoundInterestDto;
import co.invest72.investment.application.dto.CalculateMonthlyCompoundInterestResultDto;
import co.invest72.investment.domain.Investment;
import co.invest72.investment.domain.MonthlyCompoundInterest;
import co.invest72.investment.domain.amount.FixedDepositAmount;
import co.invest72.investment.domain.amount.MonthlyAmount;
import co.invest72.investment.domain.interest.AnnualInterestRate;
import co.invest72.investment.domain.period.YearlyInvestPeriod;
import co.invest72.investment.domain.tax.NonTax;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CalculateMonthlyCompoundInterest {

	// TODO: details 주입  
	public CalculateMonthlyCompoundInterestResultDto calculate(CalculateMonthlyCompoundInterestDto dto) {
		Investment investment = new MonthlyCompoundInterest(
			new FixedDepositAmount(dto.getInitialAmount()),
			new MonthlyAmount(dto.getMonthlyDeposit()),
			new YearlyInvestPeriod(dto.getInvestmentYears()),
			new AnnualInterestRate(dto.getAnnualInterestRate()),
			new NonTax()
		);
		Integer totalInvestment = investment.getInvestment();
		Integer totalInterest = investment.getTotalInterest();
		Integer totalProfit = investment.getTotalProfit();

		return CalculateMonthlyCompoundInterestResultDto.builder()
			.totalInvestment(totalInvestment)
			.totalInterest(totalInterest)
			.totalProfit(totalProfit)
			.details(new ArrayList<>())
			.build();
	}
}
