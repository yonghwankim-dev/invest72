package co.invest72.investment.domain;

import java.math.BigDecimal;

import co.invest72.money.domain.Currency;
import co.invest72.money.domain.Money;

/**
 * 환매조건부채권(RepurchaseAgreement) 투자 상품
 */
public class RepurchaseAgreement implements Investment {

	private final InvestmentAmount amount;
	private final InterestRate interestRate;

	public RepurchaseAgreement(
		InvestmentAmount amount,
		InterestRate interestRate
	) {
		this.amount = amount;
		this.interestRate = interestRate;
	}

	@Override
	public Money getPrincipal() {
		return amount.getAmount();
	}

	@Override
	public Money getPrincipal(int month) {
		return amount.getAmount();
	}

	@Override
	public Money getInterest() {
		return amount.calAnnualInterest(interestRate);
	}

	@Override
	public Money getInterest(int month) {
		if (month > getFinalMonth()) {
			return getInterest(getFinalMonth());
		}
		if (month < 0) {
			return getInterest(0);
		}
		return roundToWholeMoney.apply(amount.calMonthlyInterest(interestRate).times(month));
	}

	@Override
	public Money getProfit() {
		return null;
	}

	@Override
	public Money getProfit(int month) {
		return null;
	}

	@Override
	public Money getTotalInvestment() {
		return null;
	}

	@Override
	public Money getTotalInterest() {
		return null;
	}

	@Override
	public Money getTotalTax() {
		return null;
	}

	@Override
	public Money getTotalProfit() {
		return null;
	}

	@Override
	public int getFinalMonth() {
		// TODO: add field investPeriod  
		return 12;
	}

	@Override
	public String getTaxType() {
		return null;
	}

	@Override
	public Money getPrincipalForYear(int year) {
		return null;
	}

	@Override
	public Money getInterestForYear(int year) {
		return null;
	}

	@Override
	public Money getProfitForYear(int year) {
		return null;
	}

	@Override
	public BigDecimal getTaxRate() {
		return null;
	}

	@Override
	public Currency getCurrency() {
		return null;
	}
}
