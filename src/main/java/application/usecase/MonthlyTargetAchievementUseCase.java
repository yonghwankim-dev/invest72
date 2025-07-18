package application.usecase;

import java.time.LocalDate;

import application.request.TargetAchievementRequest;
import application.resolver.KoreanStringBasedTaxableResolver;
import application.resolver.TaxableResolver;
import application.response.TargetAchievementResponse;
import application.time.DateProvider;
import domain.amount.DefaultTargetAmount;
import domain.amount.MonthlyInvestmentAmount;
import domain.amount.TargetAmount;
import domain.amount.TargetAmountReachable;
import domain.interest_rate.AnnualInterestRate;
import domain.interest_rate.InterestRate;
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
		int initialCapital = request.initialCapital();
		TargetAmountReachable targetAmountReachable = new MonthlyInvestmentAmount(request.monthlyInvestmentAmount());
		TargetAmount targetAmount = new DefaultTargetAmount(request.targetAmount());
		InterestRate interestRate = new AnnualInterestRate(request.interestRate());

		int months = targetAmountReachable.calMonthsToReach(initialCapital, targetAmount, interestRate);
		LocalDate achievedDate = dateProvider.calAchieveDate(months);
		int principal = initialCapital + targetAmountReachable.calPrincipal(months);
		int interest = targetAmountReachable.calInterest(targetAmount, interestRate);
		TaxableFactory taxableFactory = new KoreanTaxableFactory();
		TaxableResolver taxableResolver = new KoreanStringBasedTaxableResolver(taxableFactory);
		TaxType taxType = TaxType.from(request.taxType());
		TaxRate taxRate = new FixedTaxRate(request.taxRate());
		Taxable taxable = taxableResolver.resolve(taxType, taxRate);
		int tax = taxable.applyTax(interest);
		int afterTaxInterest = interest - tax;
		int totalProfit = principal + afterTaxInterest;

		return TargetAchievementResponse.builder()
			.achievementDate(achievedDate)
			.principal(principal)
			.interest(interest)
			.tax(tax)
			.afterTaxInterest(afterTaxInterest)
			.totalProfit(totalProfit)
			.build();
	}
}
