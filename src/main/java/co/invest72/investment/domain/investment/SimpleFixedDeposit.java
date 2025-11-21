package co.invest72.investment.domain.investment;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import co.invest72.investment.application.dto.MonthlyInvestmentDetail;
import co.invest72.investment.domain.InterestRate;
import co.invest72.investment.domain.InvestPeriod;
import co.invest72.investment.domain.Investment;
import co.invest72.investment.domain.LumpSumInvestmentAmount;
import co.invest72.investment.domain.Taxable;
import lombok.extern.slf4j.Slf4j;

/**
 * 정기 예금
 * 단리로 이자를 계산하며, 세금이 적용됩니다.
 */
@Slf4j
public class SimpleFixedDeposit implements Investment {

	private final LumpSumInvestmentAmount investmentAmount;
	private final InvestPeriod investPeriod;
	private final InterestRate interestRate;
	private final Taxable taxable;
	private final List<MonthlyInvestmentDetail> details;

	public SimpleFixedDeposit(LumpSumInvestmentAmount investmentAmount, InvestPeriod investPeriod,
		InterestRate interestRate, Taxable taxable) {
		this.investmentAmount = investmentAmount;
		this.investPeriod = investPeriod;
		this.interestRate = interestRate;
		this.taxable = taxable;
		this.details = calculateDetails();
	}

	private List<MonthlyInvestmentDetail> calculateDetails() {
		List<MonthlyInvestmentDetail> result = new ArrayList<>();
		BigDecimal principal = investmentAmount.getAmount();
		BigDecimal interest = BigDecimal.ZERO;
		BigDecimal tax = BigDecimal.ZERO;
		BigDecimal profit = investmentAmount.getAmount();
		result.add(new MonthlyInvestmentDetail(0, principal, interest, tax, profit));
		for (int i = 1; i <= getFinalMonth(); i++) {
			principal = investmentAmount.getAmount();
			interest = interestRate.getMonthlyRate().multiply(principal);
			tax = taxable.applyTax(interest);
			profit = principal.add(interest).subtract(tax);
			log.info("Month {}: Principal={}, Interest={}, Tax={}, Profit={}", i, principal, interest, tax, profit);
			result.add(new MonthlyInvestmentDetail(i, principal, interest, tax, profit));
		}
		return result;
	}

	@Override
	public int getInvestment() {
		return investmentAmount.getDepositAmount();
	}

	@Override
	public int getPrincipal() {
		return getPrincipal(investPeriod.getMonths());
	}

	@Override
	public int getPrincipal(int month) {
		if (month > getFinalMonth()) {
			return getPrincipal();
		}
		if (month < 0) {
			return getPrincipal(0);
		}
		return formattedAmount(details.get(month).getPrincipal());
	}

	private boolean isOutOfRange(int month) {
		return month > investPeriod.getMonths();
	}

	private int formattedAmount(BigDecimal amount) {
		return amount.setScale(0, RoundingMode.HALF_EVEN).intValueExact();
	}

	@Override
	public int getTotalPrincipal() {
		return getPrincipal();
	}

	@Override
	public int getInterest() {
		return getInterest(getFinalMonth());
	}

	@Override
	public int getInterest(int month) {
		if (month > getFinalMonth()) {
			return getInterest();
		}
		if (month < 0) {
			return getInterest(0);
		}
		return formattedAmount(details.get(month).getInterest());
	}

	@Override
	public int getTotalInterest() {
		BigDecimal totalInterest = details.stream()
			.skip(1) // 0월은 이자가 없음
			.map(MonthlyInvestmentDetail::getInterest)
			.reduce(BigDecimal.ZERO, BigDecimal::add);
		return formattedAmount(totalInterest);
	}

	@Override
	public int getTax() {
		return getTax(getFinalMonth());
	}

	@Override
	public int getTax(int month) {
		if (month < 0) {
			return formattedAmount(details.get(0).getTax());
		}
		return formattedAmount(details.get(month).getTax());
	}

	@Override
	public int getTotalTax() {
		BigDecimal totalTax = details.stream()
			.skip(1) // 첫 번째 항목(0월)은 세금이 없으므로 건너뜁니다.
			.map(MonthlyInvestmentDetail::getTax)
			.reduce(BigDecimal.ZERO, BigDecimal::add);
		return formattedAmount(totalTax);
	}

	@Override
	public int getProfit() {
		return getProfit(getFinalMonth());
	}

	@Override
	public int getProfit(int month) {
		if (isOutOfRange(month)) {
			throw new IllegalArgumentException("Invalid month: " + month);
		}
		if (month < 0) {
			return formattedAmount(details.get(0).getProfit());
		}
		return formattedAmount(details.get(month).getProfit());
	}

	@Override
	public int getTotalProfit() {
		return getTotalPrincipal() + getTotalInterest() - getTotalTax();
	}

	@Override
	public int getFinalMonth() {
		return investPeriod.getMonths();
	}
}
