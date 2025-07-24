package application.usecase;

import java.time.LocalDate;

import application.request.TargetAchievementRequest;
import application.resolver.KoreanStringBasedTaxableResolver;
import application.resolver.TaxableResolver;
import application.response.TargetAchievementResponse;
import application.time.DateProvider;
import domain.amount.InstallmentInvestmentAmount;
import domain.amount.MonthlyInstallmentInvestmentAmount;
import domain.interest_rate.AnnualInterestRate;
import domain.interest_rate.InterestRate;
import domain.invest_period.InvestPeriod;
import domain.invest_period.MonthlyInvestPeriod;
import domain.investment.CompoundFixedInstallmentSaving;
import domain.investment.Investment;
import domain.tax.FixedTaxRate;
import domain.tax.TaxRate;
import domain.tax.Taxable;
import domain.tax.factory.KoreanTaxableFactory;
import domain.tax.factory.TaxableFactory;
import domain.type.TaxType;

/**
 * 초기 자본금과 월 투자금액을 기반으로 목표 달성 금액을 도달하는데 걸리는 시간을 계산하는 유스케이스입니다.
 */
public class MonthlyTargetAchievementUseCase implements TargetAchievementUseCase {

	private final DateProvider dateProvider;

	public MonthlyTargetAchievementUseCase(DateProvider dateProvider) {
		this.dateProvider = dateProvider;
	}

	@Override
	public TargetAchievementResponse calTargetAchievement(TargetAchievementRequest request) {
		InstallmentInvestmentAmount investmentAmount = new MonthlyInstallmentInvestmentAmount(
			request.monthlyInvestmentAmount());
		InterestRate interestRate = new AnnualInterestRate(request.interestRate());
		Taxable taxable = resolveTaxable(request);
		int month = 1;
		int totalProfit = 0;

		Investment investment = new CompoundFixedInstallmentSaving(
			investmentAmount,
			new MonthlyInvestPeriod(month),
			interestRate,
			taxable
		);

		while (totalProfit < request.targetAmount()) {
			InvestPeriod investPeriod = new MonthlyInvestPeriod(month);
			investment = new CompoundFixedInstallmentSaving(
				investmentAmount,
				investPeriod,
				interestRate,
				taxable
			);
			totalProfit = investment.getTotalProfit();
			month++;
		}

		int finalMonth = investment.getFinalMonth();
		LocalDate achieveDate = dateProvider.calAchieveDate(finalMonth);
		return TargetAchievementResponse.builder()
			.achievementDate(achieveDate)
			.principal(investment.getPrincipal())
			.interest(investment.getInterest())
			.tax(investment.getTax())
			.afterTaxInterest(investment.getInterest() - investment.getTax())
			.totalProfit(investment.getTotalProfit())
			.build();
	}

	private Taxable resolveTaxable(TargetAchievementRequest request) {
		TaxableFactory taxableFactory = new KoreanTaxableFactory();
		TaxableResolver taxableResolver = new KoreanStringBasedTaxableResolver(taxableFactory);
		TaxType taxType = TaxType.from(request.taxType());
		TaxRate taxRate = new FixedTaxRate(request.taxRate());
		return taxableResolver.resolve(taxType, taxRate);
	}
}
