package application;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.investment.Investment;
import domain.investment.SimpleFixedInstallmentSaving;

class DefaultInvestmentFactoryTest {

	private InvestmentRequestFactory investmentFactory;
	private InvestmentRequest request;
	private Investment investment;

	private void assertInstanceOfInvestment(Class<?> expectedType, Investment investment) {
		assertInstanceOf(expectedType, investment);
	}

	@BeforeEach
	void setUp() {
		investmentFactory = new DefaultInvestmentFactory();
		request = new InvestmentRequest(
			"예금",
			1_000_000,
			"year",
			1,
			"단리",
			5,
			"비과세",
			0.0
		);
	}

	@Test
	void shouldReturnInvestment_whenRequestIsSimpleFixedDeposit() {

		investment = investmentFactory.createBy(request);

		assertNotNull(investment);
		assertInstanceOfInvestment(domain.investment.SimpleFixedDeposit.class, investment);
	}

	@Test
	void shouldInstanceOfCompoundFixedDeposit_whenRequestIsCompoundFixedDeposit() {
		request = new InvestmentRequest(
			"예금",
			1_000_000,
			"year",
			1,
			"복리",
			5,
			"비과세",
			0.0
		);

		investment = investmentFactory.createBy(request);

		assertNotNull(investment);
		assertInstanceOfInvestment(domain.investment.CompoundFixedDeposit.class, investment);
	}

	@Test
	void shouldInstanceOfSimpleFixedInstallmentSaving_whenRequestIsSimpleFixedInstallmentSaving(){
		request = new InvestmentRequest(
			"적금",
			1_000_000,
			"year",
			1,
			"단리",
			5,
			"비과세",
			0.0
		);

		investment = investmentFactory.createBy(request);

		assertNotNull(investment);
		assertInstanceOfInvestment(SimpleFixedInstallmentSaving.class, investment);
	}
}
