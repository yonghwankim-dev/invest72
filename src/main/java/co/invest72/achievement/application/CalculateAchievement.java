package co.invest72.achievement.application;

import java.time.LocalDate;

import co.invest72.achievement.domain.AchievementDateCalculator;
import co.invest72.achievement.domain.time.AchievementInvestmentCalculator;
import co.invest72.investment.domain.InstallmentInvestmentAmount;
import co.invest72.investment.domain.InterestRate;
import co.invest72.investment.domain.InvestPeriod;
import co.invest72.investment.domain.Investment;
import co.invest72.investment.domain.TaxRate;
import co.invest72.investment.domain.Taxable;
import co.invest72.investment.domain.TaxableResolver;
import co.invest72.investment.domain.amount.MonthlyInstallmentInvestmentAmount;
import co.invest72.investment.domain.interest.AnnualInterestRate;
import co.invest72.investment.domain.investment.CompoundFixedInstallmentSaving;
import co.invest72.investment.domain.period.MonthlyInvestPeriod;
import co.invest72.investment.domain.tax.FixedTaxRate;
import co.invest72.investment.domain.tax.TaxType;

/**
 * 다양한 투자 방법(예: 월 납입, 자본금 등) 등을 통해서 목표 달성 금액을 도달하는데 걸리는 시간을 계산하는 유스케이스입니다.
 */
public class CalculateAchievement {

	private final AchievementDateCalculator achievementDateCalculator;
	private final TaxableResolver taxableResolver;
	private final AchievementInvestmentCalculator calculator;

	public CalculateAchievement(AchievementDateCalculator achievementDateCalculator, TaxableResolver taxableResolver,
		AchievementInvestmentCalculator calculator) {
		this.achievementDateCalculator = achievementDateCalculator;
		this.taxableResolver = taxableResolver;
		this.calculator = calculator;
	}

	/**
	 * 목표 달성 금액을 도달하는데 걸리는 시간을 계산합니다.
	 * 당월에 투자하는 것으로 가정합니다.
	 * 예를 들어 2025년 1월 1일에 월 투자금액을 투자한다면 1월을 포함한다.
	 *
	 * @param request 목표 달성 금액을 도달하기 위한 요청 정보
	 * @return 목표 달성 금액을 도달하는 날짜
	 */
	public AchievementResponse calAchievement(AchievementRequest request) {
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

		LocalDate achieveDate = achievementDateCalculator.addMonth(month);
		int afterTaxInterest = investment.getInterest() - investment.getTax();
		return AchievementResponse.builder()
			.achievementDate(achieveDate)
			.principal(investment.getPrincipal())
			.interest(investment.getInterest())
			.tax(investment.getTax())
			.afterTaxInterest(afterTaxInterest)
			.totalProfit(investment.getTotalProfit())
			.build();
	}

	private Taxable resolveTaxable(AchievementRequest request) {
		TaxType taxType = TaxType.from(request.taxType());
		TaxRate taxRate = new FixedTaxRate(request.taxRate());
		return taxableResolver.resolve(taxType, taxRate);
	}

	public record AchievementResponse(LocalDate achievedDate, int principal, int interest, int tax,
									  int afterTaxInterest, int totalProfit) {

		public AchievementResponse(TargetAchievementResponseBuilder builder) {
			this(builder.achievedDate,
				builder.principal,
				builder.interest,
				builder.tax,
				builder.afterTaxInterest,
				builder.totalProfit
			);
		}

		public static TargetAchievementResponseBuilder builder() {
			return new TargetAchievementResponseBuilder();
		}

		public static class TargetAchievementResponseBuilder {
			private LocalDate achievedDate;
			private int principal;
			private int interest;
			private int tax;
			private int afterTaxInterest;
			private int totalProfit;

			public TargetAchievementResponseBuilder achievementDate(LocalDate achievedDate) {
				this.achievedDate = achievedDate;
				return this;
			}

			public TargetAchievementResponseBuilder principal(int principal) {
				this.principal = principal;
				return this;
			}

			public TargetAchievementResponseBuilder interest(int interest) {
				this.interest = interest;
				return this;
			}

			public TargetAchievementResponseBuilder tax(int tax) {
				this.tax = tax;
				return this;
			}

			public TargetAchievementResponseBuilder afterTaxInterest(int afterTaxInterest) {
				this.afterTaxInterest = afterTaxInterest;
				return this;
			}

			public TargetAchievementResponseBuilder totalProfit(int totalProfit) {
				this.totalProfit = totalProfit;
				return this;
			}

			public AchievementResponse build() {
				return new AchievementResponse(this);
			}
		}
	}

	public record AchievementRequest(int initialCapital, int targetAmount,
									 int monthlyInvestmentAmount, double interestRate,
									 String taxType, double taxRate) {

		public AchievementRequest(TargetAchievementRequestBuilder builder) {
			this(builder.initialCapital,
				builder.targetAmount,
				builder.monthlyInvestmentAmount,
				builder.interestRate,
				builder.taxType,
				builder.taxRate
			);
		}

		public static TargetAchievementRequestBuilder builder() {
			return new TargetAchievementRequestBuilder();
		}

		public static class TargetAchievementRequestBuilder {
			private int initialCapital;
			private int targetAmount;
			private int monthlyInvestmentAmount;
			private double interestRate;
			private String taxType;
			private double taxRate;

			public TargetAchievementRequestBuilder initialCapital(int initialCapital) {
				this.initialCapital = initialCapital;
				return this;
			}

			public TargetAchievementRequestBuilder targetAmount(int targetAmount) {
				this.targetAmount = targetAmount;
				return this;
			}

			public TargetAchievementRequestBuilder monthlyInvestmentAmount(int monthlyInvestmentAmount) {
				this.monthlyInvestmentAmount = monthlyInvestmentAmount;
				return this;
			}

			public TargetAchievementRequestBuilder interestRate(double interestRate) {
				this.interestRate = interestRate;
				return this;
			}

			public TargetAchievementRequestBuilder taxType(String taxType) {
				this.taxType = taxType;
				return this;
			}

			public TargetAchievementRequestBuilder taxRate(double taxRate) {
				this.taxRate = taxRate;
				return this;
			}

			public AchievementRequest build() {
				return new AchievementRequest(this);
			}
		}
	}
}
