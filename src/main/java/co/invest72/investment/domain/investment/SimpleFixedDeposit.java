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

/**
 * 정기 예금
 * 단리로 이자를 계산하며, 세금이 적용됩니다.
 */
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
		for (int i = 1; i <= getFinalMonth(); i++) {
			BigDecimal principal = investmentAmount.getAmount();
			BigDecimal interest = interestRate.getMonthlyRate().multiply(principal);
			BigDecimal tax = BigDecimal.valueOf(
				taxable.applyTax(interest.setScale(0, RoundingMode.HALF_EVEN).intValue()));
			BigDecimal profit = principal.add(interest).subtract(tax);
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
		if (isOutOfRange(month)) {
			throw new IllegalArgumentException("Invalid month: " + month);
		}
		if (month <= 1) {
			return formattedAmount(details.get(0).getPrincipal());
		}
		return formattedAmount(details.get(month - 1).getPrincipal());
	}

	private boolean isOutOfRange(int month) {
		return month < 0 || month > investPeriod.getMonths();
	}

	private int formattedAmount(BigDecimal amount) {
		return amount.setScale(0, RoundingMode.HALF_EVEN).intValueExact();
	}

	@Override
	public int getInterest() {
		return getInterest(investPeriod.getMonths());
	}

	@Override
	public int getInterest(int month) {
		if (isOutOfRange(month)) {
			throw new IllegalArgumentException("Invalid month: " + month);
		}
		if (month <= 1) {
			return formattedAmount(details.get(0).getInterest());
		}
		return formattedAmount(details.get(month - 1).getInterest());
	}

	@Override
	public int getTax() {
		return applyTax(getInterest());
	}

	private int applyTax(int interest) {
		return taxable.applyTax(interest);
	}

	@Override
	public int getTax(int month) {
		return applyTax(getInterest(month));
	}

	@Override
	public int getTotalProfit() {
		return getTotalProfit(investPeriod.getMonths());
	}

	@Override
	public int getTotalProfit(int month) {
		if (isOutOfRange(month)) {
			throw new IllegalArgumentException("Invalid month: " + month);
		}
		if (month <= 1) {
			return formattedAmount(details.get(0).getProfit());
		}
		return formattedAmount(details.get(month - 1).getProfit());
	}

	@Override
	public int getFinalMonth() {
		return investPeriod.getMonths();
	}
}
