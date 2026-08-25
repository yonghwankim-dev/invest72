package co.invest72.investment.domain;

import java.math.BigDecimal;

import co.invest72.investment.domain.interest.AnnualInterestRate;
import co.invest72.money.domain.Currency;
import co.invest72.money.domain.Money;

/**
 * 환매조건부채권(RepurchaseAgreement) 투자 상품
 */
public class RepurchaseAgreement implements Investment {

	private final InvestmentAmount amount;

	public RepurchaseAgreement(InvestmentAmount amount) {
		this.amount = amount;
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
		InterestRate interestRate = new AnnualInterestRate(BigDecimal.valueOf(0.05));
		return amount.calAnnualInterest(interestRate);
	}

	@Override
	public Money getInterest(int month) {
		return null;
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
		return 0;
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
