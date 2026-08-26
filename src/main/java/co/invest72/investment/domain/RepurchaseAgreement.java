package co.invest72.investment.domain;

import java.math.BigDecimal;
import java.util.List;

import co.invest72.investment.domain.interest.InterestType;
import co.invest72.investment.domain.investment.InvestmentDetail;
import co.invest72.investment.domain.investment.factory.FixedDepositDetailFactory;
import co.invest72.investment.domain.investment.factory.InvestmentDetailFactory;
import co.invest72.money.domain.Currency;
import co.invest72.money.domain.Money;

/**
 * 환매조건부채권(RepurchaseAgreement) 투자 상품
 */
public class RepurchaseAgreement implements Investment {

	private final InvestmentAmount amount;
	private final InvestPeriod investPeriod;
	private final Taxable taxable;
	private final List<InvestmentDetail> details;
	private final List<InvestmentDetail> yearlyDetails;

	public RepurchaseAgreement(
		InvestmentAmount amount,
		InterestRate interestRate,
		InvestPeriod investPeriod,
		Taxable taxable
	) {
		this.amount = amount;
		this.investPeriod = investPeriod;
		this.taxable = taxable;
		InvestmentDetailFactory factory = new FixedDepositDetailFactory(
			amount,
			interestRate,
			investPeriod,
			InterestType.COMPOUND
		);
		this.details = factory.createMonthlyDetails();
		this.yearlyDetails = factory.createYearlyDetails();
	}

	@Override
	public Money getPrincipal() {
		return getPrincipal(getFinalMonth());
	}

	@Override
	public Money getPrincipal(int month) {
		if (month > getFinalMonth()) {
			return getPrincipal(getFinalMonth());
		}
		if (month < 0) {
			return getPrincipal(0);
		}
		return roundToWholeMoney.apply(details.get(month).getPrincipal());
	}

	@Override
	public Money getInterest() {
		return roundToWholeMoney.apply(getInterest(getFinalMonth()));
	}

	@Override
	public Money getInterest(int month) {
		if (month > getFinalMonth()) {
			return getInterest(getFinalMonth());
		}
		if (month < 0) {
			return getInterest(0);
		}
		return roundToWholeMoney.apply(details.get(month).getInterest());
	}

	@Override
	public Money getProfit() {
		return getProfit(getFinalMonth());
	}

	@Override
	public Money getProfit(int month) {
		if (month > getFinalMonth()) {
			return getProfit(getFinalMonth());
		}
		if (month < 0) {
			return getProfit(0);
		}
		return roundToWholeMoney.apply(details.get(month).getProfit());
	}

	@Override
	public Money getTotalInvestment() {
		return amount.getAmount();
	}

	@Override
	public Money getTotalInterest() {
		Money totalInterest = details.stream()
			.skip(1) // 0월은 이자가 없음
			.map(InvestmentDetail::getInterest)
			.reduce(Money::add)
			.orElseGet(() -> Money.of(BigDecimal.ZERO, amount.getAmount().getCurrency()));
		return roundToWholeMoney.apply(totalInterest);
	}

	@Override
	public Money getTotalTax() {
		Money tax = taxable.applyTax(getTotalInterest());
		return roundToWholeMoney.apply(tax);
	}

	@Override
	public Money getTotalProfit() {
		Money principal = details.get(getFinalMonth()).getPrincipal();
		Money interest = details.get(getFinalMonth()).getInterest();
		Money tax = getTotalTax();
		Money totalProfit = principal.add(interest).subtract(tax);
		return roundToWholeMoney.apply(totalProfit);
	}

	@Override
	public int getFinalMonth() {
		return investPeriod.getMonths();
	}

	@Override
	public String getTaxType() {
		return taxable.getTaxType();
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
