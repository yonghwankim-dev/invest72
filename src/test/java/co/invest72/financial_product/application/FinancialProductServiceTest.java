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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import co.invest72.common.time.LocalDateProvider;
import co.invest72.exchange_rate.domain.entity.ExchangeRate;
import co.invest72.exchange_rate.domain.service.Bank;
import co.invest72.exchange_rate.domain.service.ExchangeRateService;
import co.invest72.exchange_rate.infrastructure.persistence.InMemoryExchangeRateRepository;
import co.invest72.financial_product.domain.FinancialProduct;
import co.invest72.financial_product.domain.FinancialProductRepository;
import co.invest72.financial_product.domain.IdGenerator;
import co.invest72.financial_product.domain.ProductAmount;
import co.invest72.financial_product.domain.ProductAnnualInterestRate;
import co.invest72.financial_product.domain.ProductInterestType;
import co.invest72.financial_product.domain.ProductInvestmentType;
import co.invest72.financial_product.domain.ProductMonths;
import co.invest72.financial_product.domain.ProductTaxRate;
import co.invest72.financial_product.domain.ProductTaxType;
import co.invest72.financial_product.domain.RepurchaseAgreementProduct;
import co.invest72.financial_product.domain.entity.FinancialProductData;
import co.invest72.financial_product.domain.service.FinancialProductCalculator;
import co.invest72.financial_product.infrastructure.mapper.ProductAmountMapper;
import co.invest72.financial_product.presentation.dto.request.FinancialProductRequest;
import co.invest72.financial_product.presentation.dto.response.DetailedFinancialProductResponse;
import co.invest72.financial_product.presentation.dto.response.ProductCurrency;
import co.invest72.investment.application.InvestmentFactory;
import co.invest72.investment.domain.interest.InterestType;
import co.invest72.investment.domain.investment.InvestmentType;
import co.invest72.investment.domain.tax.TaxType;
import co.invest72.money.domain.Currency;
import co.invest72.money.domain.Money;
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
	private User user;

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
		user = new User("user1@gmail.com", "user1", UUID.randomUUID().toString());
	}

	@Test
	@DisplayName("RP 상품 생성")
	void should_create_rp_product() {
		// given
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

	@Test
	@DisplayName("상품 상세 조회")
	void should_return_detailed_product() {
		// given
		String productId = UUID.randomUUID().toString();
		LocalDate startDate = LocalDate.of(2026, 8, 27);
		Money amount = Money.won(1_000_000);
		FinancialProduct product = RepurchaseAgreementProduct.builder()
			.id(productId)
			.userId(user.getId())
			.name("미래에셋증권 RP")
			.productInvestmentType(ProductInvestmentType.from(InvestmentType.RP))
			.amount(ProductAmount.from(amount))
			.months(new ProductMonths(12))
			.productAnnualInterestRate(new ProductAnnualInterestRate(BigDecimal.valueOf(0.03)))
			.productInterestType(ProductInterestType.from(InterestType.COMPOUND))
			.productTaxType(ProductTaxType.from(TaxType.STANDARD))
			.productTaxRate(new ProductTaxRate(BigDecimal.valueOf(0.154)))
			.startDate(startDate)
			.createdAt(startDate.atStartOfDay())
			.build();

		BDDMockito.given(financialProductRepository.findByProductId(productId))
			.willReturn(product);
		BDDMockito.given(localDateProvider.now())
			.willReturn(startDate);

		Currency currency = amount.getCurrency();
		BigDecimal rate = BigDecimal.valueOf(1);
		BDDMockito.given(exchangeRateService.findExchangeRate(currency.getCode()))
			.willReturn(new ExchangeRate(currency.getCode(), currency.getName(), rate));
		// when
		DetailedFinancialProductResponse productDetail = service.getProductDetail(user, productId);
		// then
		DetailedFinancialProductResponse expected = DetailedFinancialProductResponse.builder()
			.id(productId)
			.userId(user.getId())
			.name("미래에셋증권 RP")
			.investmentType(InvestmentType.RP.name())
			.amount(amount.getValue())
			.months(12)
			.paymentDay(null)
			.interestRate(BigDecimal.valueOf(0.03))
			.interestType(InterestType.COMPOUND.name())
			.taxType(TaxType.STANDARD.name())
			.taxRate(BigDecimal.valueOf(0.154))
			.startDate(startDate)
			.createdAt(startDate.atStartOfDay())
			.expirationDate(startDate.plusMonths(12L))
			.balance(amount.getValue())
			.progress(BigDecimal.ZERO)
			.remainingDays(365L)
			.productCurrency(ProductCurrency.from(currency))
			.build();
		Assertions.assertThat(productDetail)
			.usingRecursiveComparison()
			.withComparatorForType(BigDecimal::compareTo, BigDecimal.class)
			.isEqualTo(expected);
	}

	@Test
	@DisplayName("RP 상품 수정")
	void should_update_product() {
		// given
		String productId = UUID.randomUUID().toString();
		String changeName = "변경된 미래에셋증권 RP";
		BigDecimal changeAmount = BigDecimal.valueOf(2_000_000);
		int changeMonths = 24;
		BigDecimal changeInterestRate = BigDecimal.valueOf(0.05);
		String changeInterestType = InterestType.SIMPLE.name();
		String changeTaxType = TaxType.NON_TAX.name();
		BigDecimal changeTaxRate = BigDecimal.ZERO;
		LocalDate changeStartDate = LocalDate.of(2026, 8, 1);
		FinancialProductData dto = FinancialProductRequest.builder()
			.name(changeName)
			.investmentType(InvestmentType.RP.name())
			.amount(changeAmount)
			.months(changeMonths)
			.paymentDay(null)
			.interestRate(changeInterestRate)
			.interestType(changeInterestType)
			.taxType(changeTaxType)
			.taxRate(changeTaxRate)
			.startDate(changeStartDate)
			.currencyCode(Currency.won().getCode())
			.build();
		Money amount = Money.won(1_000_000);
		FinancialProduct originalProduct = RepurchaseAgreementProduct.builder()
			.id(productId)
			.userId(user.getId())
			.name("미래에셋증권 RP")
			.productInvestmentType(ProductInvestmentType.from(InvestmentType.RP))
			.amount(ProductAmount.from(amount))
			.months(new ProductMonths(12))
			.productAnnualInterestRate(new ProductAnnualInterestRate(BigDecimal.valueOf(0.03)))
			.productInterestType(ProductInterestType.from(InterestType.COMPOUND))
			.productTaxType(ProductTaxType.from(TaxType.STANDARD))
			.productTaxRate(new ProductTaxRate(BigDecimal.valueOf(0.154)))
			.startDate(changeStartDate)
			.createdAt(changeStartDate.atStartOfDay())
			.build();

		BDDMockito.given(financialProductRepository.findByProductId(productId))
			.willReturn(originalProduct);
		// when
		service.updateProduct(user, productId, dto);
		// then
		Assertions.assertThat(originalProduct.getName()).isEqualTo(changeName);
		Assertions.assertThat(originalProduct.getAmount()).isEqualTo(ProductAmount.won(changeAmount));
		Assertions.assertThat(originalProduct.getMonths()).isEqualTo(new ProductMonths(24));
		Assertions.assertThat(originalProduct.getProductAnnualInterestRate())
			.isEqualTo(new ProductAnnualInterestRate(changeInterestRate));
		Assertions.assertThat(originalProduct.getProductInterestType())
			.isEqualTo(ProductInterestType.from(InterestType.SIMPLE));
		Assertions.assertThat(originalProduct.getProductTaxType())
			.isEqualTo(ProductTaxType.from(TaxType.NON_TAX));
		Assertions.assertThat(originalProduct.getProductTaxRate())
			.isEqualTo(new ProductTaxRate(BigDecimal.ZERO));
		Assertions.assertThat(originalProduct.getStartDate())
			.isEqualTo(changeStartDate);
	}

	@Test
	@DisplayName("상품 삭제")
	void should_delete_product() {
		// given
		String productId = UUID.randomUUID().toString();
		LocalDate startDate = LocalDate.of(2026, 8, 27);
		FinancialProduct product = RepurchaseAgreementProduct.builder()
			.id(productId)
			.userId(user.getId())
			.name("미래에셋증권 RP")
			.productInvestmentType(ProductInvestmentType.from(InvestmentType.RP))
			.amount(ProductAmount.from(Money.won(1_000_000)))
			.months(new ProductMonths(12))
			.productAnnualInterestRate(new ProductAnnualInterestRate(BigDecimal.valueOf(0.03)))
			.productInterestType(ProductInterestType.from(InterestType.COMPOUND))
			.productTaxType(ProductTaxType.from(TaxType.STANDARD))
			.productTaxRate(new ProductTaxRate(BigDecimal.valueOf(0.154)))
			.startDate(startDate)
			.createdAt(startDate.atStartOfDay())
			.build();
		BDDMockito.given(financialProductRepository.findByProductId(productId))
			.willReturn(product);
		// when
		service.deleteProduct(user, productId);
		// then
		BDDMockito.verify(financialProductRepository, Mockito.times(1))
			.deleteByProductId(productId);
	}
}
