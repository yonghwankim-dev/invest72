package co.invest72.financial_product.application;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import co.invest72.common.time.LocalDateProvider;
import co.invest72.exchange_rate.domain.service.Bank;
import co.invest72.exchange_rate.domain.service.ExchangeRateService;
import co.invest72.exchange_rate.infrastructure.persistence.InMemoryExchangeRateRepository;
import co.invest72.financial_product.domain.FinancialProductRepository;
import co.invest72.financial_product.domain.IdGenerator;
import co.invest72.financial_product.domain.RepurchaseAgreementProduct;
import co.invest72.financial_product.domain.entity.FinancialProductData;
import co.invest72.financial_product.domain.service.FinancialProductCalculator;
import co.invest72.financial_product.infrastructure.mapper.ProductAmountMapper;
import co.invest72.financial_product.presentation.dto.request.FinancialProductRequest;
import co.invest72.investment.application.InvestmentFactory;
import co.invest72.investment.domain.interest.InterestType;
import co.invest72.investment.domain.investment.InvestmentType;
import co.invest72.investment.domain.tax.TaxType;
import co.invest72.money.domain.Currency;
import co.invest72.money.infrastructure.MoneyMapper;
import co.invest72.user.domain.User;

@ExtendWith(MockitoExtension.class)
class FinancialProductServiceTest {

	private FinancialProductService service;

	@Mock
	private FinancialProductRepository financialProductRepository;

	@Mock
	private LocalDateProvider localDateProvider;

	@Mock
	private ExchangeRateService exchangeRateService;

	@Mock
	private IdGenerator idGenerator;

	@BeforeEach
	void setUp() {
		InvestmentFactory investmentFactory = new InvestmentFactory(
			new ProductAmountMapper(exchangeRateService),
			exchangeRateService
		);
		FinancialProductFactory financialProductFactory = new FinancialProductFactory(localDateProvider, idGenerator);
		FinancialProductCalculator financialProductCalculator = new FinancialProductCalculator(
			new InMemoryExchangeRateRepository());
		MoneyMapper moneyMapper = new MoneyMapper();
		Bank bank = new Bank(exchangeRateService);
		service = new FinancialProductService(
			financialProductRepository,
			localDateProvider,
			investmentFactory,
			financialProductFactory,
			financialProductCalculator,
			moneyMapper,
			bank,
			exchangeRateService
		);
	}

	@Test
	@DisplayName("RP 상품 생성")
	void should_create_rp_product() {
		// given
		User user = new User("user1@gmail.com", "user1", UUID.randomUUID().toString());
		LocalDate startDate = LocalDate.of(2026, 8, 27);
		FinancialProductData dto = FinancialProductRequest.builder()
			.name("미래에셋증권 RP")
			.investmentType(InvestmentType.RP.name())
			.amount(BigDecimal.valueOf(1_000_000))
			.months(12)
			.paymentDay(null)
			.interestRate(BigDecimal.valueOf(0.03))
			.interestType(InterestType.COMPOUND.name())
			.taxType(TaxType.STANDARD.name())
			.taxRate(BigDecimal.valueOf(0.154))
			.startDate(startDate)
			.currencyCode(Currency.won().getCode())
			.userId(user.getId())
			.build();

		String productId = UUID.randomUUID().toString();
		BDDMockito.given(idGenerator.generateId())
			.willReturn(productId);
		BDDMockito.given(localDateProvider.nowDateTime())
			.willReturn(startDate.atStartOfDay());
		BDDMockito.given(financialProductRepository.save(ArgumentMatchers.any(RepurchaseAgreementProduct.class)))
			.willReturn(productId);
		// when
		String actualProductId = service.createProduct(user, dto);
		// then
		Assertions.assertThat(actualProductId).isEqualTo(productId);
	}
}
