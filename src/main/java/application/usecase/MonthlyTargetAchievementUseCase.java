package application.usecase;

import java.time.LocalDate;

import application.InvestmentCalculator;
import application.request.TargetAchievementRequest;
import application.resolver.TaxableResolver;
import application.response.TargetAchievementResponse;
import application.time.DateProvider;
import co.invest72.investment.domain.Investment;
import co.invest72.investment.domain.investment.CompoundFixedInstallmentSaving;
import co.invest72.investment.domain.InstallmentInvestmentAmount;
import co.invest72.investment.domain.amount.MonthlyInstallmentInvestmentAmount;
import co.invest72.investment.domain.interest.AnnualInterestRate;
import co.invest72.investment.domain.InterestRate;
import co.invest72.investment.domain.InvestPeriod;
import co.invest72.investment.domain.period.MonthlyInvestPeriod;
import co.invest72.investment.domain.tax.FixedTaxRate;
import co.invest72.investment.domain.TaxRate;
import co.invest72.investment.domain.Taxable;
import co.invest72.investment.domain.tax.TaxType;

/**
 * 초기 자본금과 월 투자금액을 기반으로 목표 달성 금액을 도달하는데 걸리는 시간을 계산하는 유스케이스입니다.
 */
public class MonthlyTargetAchievementUseCase implements TargetAchievementUseCase {

	private final DateProvider dateProvider;
	private final TaxableResolver taxableResolver;
	private final InvestmentCalculator calculator;

	public MonthlyTargetAchievementUseCase(DateProvider dateProvider, TaxableResolver taxableResolver,
		InvestmentCalculator calculator) {
		this.dateProvider = dateProvider;
		this.taxableResolver = taxableResolver;
		this.calculator = calculator;
	}

	@Override
	public TargetAchievementResponse calTargetAchievement(TargetAchievementRequest request) {
		int month = calculator.calMonth(request);
		InstallmentInvestmentAmount investmentAmount = new MonthlyInstallmentInvestmentAmount(
			request.monthlyInvestmentAmount());
		InvestPeriod investPeriod = new MonthlyInvestPeriod(month);
		InterestRate interestRate = new AnnualInterestRate(request.interestRate());
		Taxable taxable = resolveTaxable(request);
		Investment investment = new CompoundFixedInstallmentSaving(
			investmentAmount,
			investPeriod,
			interestRate,
			taxable
		);

		LocalDate achieveDate = dateProvider.calAchieveDate(month);
		int afterTaxInterest = investment.getInterest() - investment.getTax();
		return TargetAchievementResponse.builder()
			.achievementDate(achieveDate)
			.principal(investment.getPrincipal())
			.interest(investment.getInterest())
			.tax(investment.getTax())
			.afterTaxInterest(afterTaxInterest)
			.totalProfit(investment.getTotalProfit())
			.build();
	}

	private Taxable resolveTaxable(TargetAchievementRequest request) {
		TaxType taxType = TaxType.from(request.taxType());
		TaxRate taxRate = new FixedTaxRate(request.taxRate());
		return taxableResolver.resolve(taxType, taxRate);
	}
}
