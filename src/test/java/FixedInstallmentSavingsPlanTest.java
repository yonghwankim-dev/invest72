import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FixedInstallmentSavingsPlanTest {

	@Test
	void created(){
		// 월 투자 금액(원)
		InvestmentAmount investmentAmount = new MonthlyInvestmentAmount(1_000_000);
		// 투자 기간
		InvestPeriod investPeriod = new MonthlyInvestPeriod(12);
		// 연 수익율
		InterestRate annualInterestRateRate = new AnnualInterestRate(0.05);
		TaxableFactory taxableFactory = new KoreanTaxableFactory();
		// 세금 적용 방식
		Taxable taxable = taxableFactory.createNonTax();
		// 이자 계산 방식
		Interest interest = new CompoundInterest(investmentAmount, investPeriod, annualInterestRateRate, taxable);

		Investment investment = new FixedInstallmentSavingsPlan(interest);

		assertNotNull(investment);
	}

	@Test
	void shouldReturnAmount(){
		// 월 투자 금액(원)
		InvestmentAmount investmentAmount = new MonthlyInvestmentAmount(1_000_000);
		// 투자 기간
		InvestPeriod investPeriod = new MonthlyInvestPeriod(12);
		// 연 수익율
		InterestRate annualInterestRateRate = new AnnualInterestRate(0.05);
		TaxableFactory taxableFactory = new KoreanTaxableFactory();
		// 세금 적용 방식
		Taxable taxable = taxableFactory.createNonTax();
		// 이자 계산 방식
		Interest interest = new CompoundInterest(investmentAmount, investPeriod, annualInterestRateRate, taxable);

		Investment investment = new FixedInstallmentSavingsPlan(interest);

		int amount = investment.getAmount();

		int expectedAmount = 12_330_017;
		assertEquals(expectedAmount, amount);
	}
}
