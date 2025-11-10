package co.invest72.investment.presentation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.invest72.investment.domain.amount.AmountType;
import co.invest72.investment.presentation.request.CalculateInvestmentRequest;
import util.TestFileUtils;

@SpringBootTest
class InvestmentRestControllerTest {
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private WebApplicationContext context;

	public static Stream<Arguments> invalidCalculateInvestmentRequestFilePaths() {
		Path dir = Paths.get("src/test/resources/calculate_investment_request");

		try (Stream<Path> files = Files.list(dir)) {
			return files
				.filter(path -> path.toString().endsWith(".json"))
				.map(path -> Arguments.of(path.toString(), path.getFileName().toString().replace(".json", "")))
				.toList()
				.stream();
		} catch (Exception e) {
			throw new IllegalArgumentException("Error reading test files", e);
		}
	}

	private MockMvc createMockMvc() {
		return MockMvcBuilders.webAppContextSetup(context)
			.alwaysDo(print())
			.build();
	}

	@BeforeEach
	void setUp() {
		this.mockMvc = createMockMvc();
	}

	@Test
	void calculateExpiration_whenTypeIsFixedDeposit() throws Exception {
		CalculateInvestmentRequest request = CalculateInvestmentRequest.builder()
			.type("예금")
			.amountType(AmountType.ONE_TIME.getDescription())
			.amount(1_000_000)
			.periodType("년")
			.periodValue(1)
			.interestType("단리")
			.annualInterestRate(0.05)
			.taxType("일반과세")
			.taxRate(0.154)
			.build();
		mockMvc.perform(post("/investments/calculate/expiration")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.totalProfitAmount").value(1042300))
			.andExpect(jsonPath("$.totalPrincipalAmount").value(1000000))
			.andExpect(jsonPath("$.interest").value(50000))
			.andExpect(jsonPath("$.tax").value(7700));
	}

	@Test
	void calculateExpiration_whenTypeIsInstallment() throws Exception {
		CalculateInvestmentRequest request = CalculateInvestmentRequest.builder()
			.type("적금")
			.amountType(AmountType.MONTHLY.getDescription())
			.amount(1_000_000)
			.periodType("년")
			.periodValue(1)
			.interestType("단리")
			.annualInterestRate(0.05)
			.taxType("일반과세")
			.taxRate(0.154)
			.build();
		mockMvc.perform(post("/investments/calculate/expiration")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.totalProfitAmount").value(12274950))
			.andExpect(jsonPath("$.totalPrincipalAmount").value(12000000))
			.andExpect(jsonPath("$.interest").value(325000))
			.andExpect(jsonPath("$.tax").value(50050));
	}

	@ParameterizedTest(name = "{1}")
	@MethodSource(value = "invalidCalculateInvestmentRequestFilePaths")
	void calculateExpiration_whenEmptyRequest_thenReturnErrorResponse(String filePath, String ignored) throws
		Exception {
		String json = TestFileUtils.readFile(filePath);
		mockMvc.perform(post("/investments/calculate/expiration")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
			.andExpect(status().isBadRequest());
	}
}
