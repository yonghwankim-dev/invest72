package co.invest72.investment.application;

import java.math.BigDecimal;

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

	public CalculateMonthlyCompoundInterestResultDto calculate(CalculateMonthlyCompoundInterestDto dto) {
		Investment investment = new MonthlyCompoundInterest(
			new FixedDepositAmount(dto.getInitialAmount()),
			new MonthlyAmount(dto.getMonthlyDeposit()),
			new YearlyInvestPeriod(dto.getInvestmentYears()),
			new AnnualInterestRate(dto.getAnnualInterestRate()),
			new NonTax()
		);
		Integer totalPrincipal = investment.getPrincipal();
		Integer totalInterest = investment.getInterest();
		Integer totalProfit = investment.getTotalProfit();
		return CalculateMonthlyCompoundInterestResultDto.builder()
			.totalPrincipal(totalPrincipal)
			.totalInterest(totalInterest)
			.totalProfit(totalProfit)
			.build();
	}

	private static BigDecimal calTotalPrincipal(BigDecimal initialAmount, BigDecimal monthlyDeposit,
		BigDecimal months) {
		return initialAmount.add(monthlyDeposit.multiply(months));
	}
}
