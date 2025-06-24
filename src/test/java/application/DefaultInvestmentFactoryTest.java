package application;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import domain.investment.Investment;

class DefaultInvestmentFactoryTest {

	@Test
	void shouldReturnInvestment_whenRequestIsSimpleFixedDeposit() {
		InvestmentRequestFactory investmentFactory = new DefaultInvestmentFactory();
		InvestmentRequest request = new InvestmentRequest(
			"예금",
			1_000_000,
			"year",
			1,
			"단리",
			5,
			"비과세",
			0.0
		);

		Investment investment = investmentFactory.createBy(request);

		assertNotNull(investment);
		assertInstanceOf(domain.investment.SimpleFixedDeposit.class, investment);
	}

	@Test
	void shouldInstanceOfCompoundFixedDeposit_whenRequestIsCompoundFixedDeposit() {
		InvestmentRequestFactory investmentFactory = new DefaultInvestmentFactory();
		InvestmentRequest request = new InvestmentRequest(
			"예금",
			1_000_000,
			"year",
			1,
			"복리",
			5,
			"비과세",
			0.0
		);

		Investment investment = investmentFactory.createBy(request);

		assertNotNull(investment);
		assertInstanceOf(domain.investment.CompoundFixedDeposit.class, investment);
	}
}
