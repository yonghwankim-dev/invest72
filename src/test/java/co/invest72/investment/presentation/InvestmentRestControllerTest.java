package co.invest72.investment.presentation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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

	public static Stream<Arguments> validCalculateInvestmentRequests() {
		String filePath = "src/test/resources/calculate_investment_request/data.csv";
		List<Map<String, Object>> jsonList = TestFileUtils.readCsvFile(filePath);
		List<Arguments> arguments = new ArrayList<>();
		for (Map<String, Object> data : jsonList) {
			Map<String, Object> request = new LinkedHashMap<>();
			Map<String, Object> expected = new LinkedHashMap<>();
			for (Map.Entry<String, Object> entry : data.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				if (key.startsWith("expected")) {
					expected.put(key, value);
				} else {
					request.put(key, value);
				}
			}
			arguments.add(Arguments.of(request, expected));
		}
		return arguments.stream();
	}

	public static Stream<Arguments> invalidCalculateInvestmentRequests() {
		String filePath = "src/test/resources/calculate_investment_request/invalid_data.csv";
		return TestFileUtils.readCsvFile(filePath).stream()
			.map(Arguments::of);
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

	@ParameterizedTest
	@MethodSource(value = "validCalculateInvestmentRequests")
	void calculateExpiration(Map<String, Object> request, Map<String, Object> expected) throws Exception {
		mockMvc.perform(post("/investments/calculate/expiration")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.totalProfitAmount").value(expected.get("expectedTotalProfitAmount")))
			.andExpect(jsonPath("$.totalPrincipalAmount").value(expected.get("expectedTotalPrincipalAmount")))
			.andExpect(jsonPath("$.interest").value(expected.get("expectedInterest")))
			.andExpect(jsonPath("$.tax").value(expected.get("expectedTax")));
	}

	@ParameterizedTest
	@MethodSource(value = "invalidCalculateInvestmentRequests")
	void calculateExpiration_whenEmptyRequest_thenReturnErrorResponse(Map<String, Object> request) throws Exception {
		mockMvc.perform(post("/investments/calculate/expiration")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isBadRequest());
	}
}
