package co.invest72.investment.presentation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.invest72.investment.presentation.request.CalculateInvestmentRequest;

@SpringBootTest
class InvestmentRestControllerTest {
	private MockMvc mockMvc;

	@Autowired
	private InvestmentRestController controller;

	@Autowired
	private ObjectMapper objectMapper;

	private MockMvc createMockMvc(Object controller) {
		return MockMvcBuilders.standaloneSetup(controller)
			.setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
			.alwaysDo(print())
			.build();
	}

	@BeforeEach
	void setUp() {
		this.mockMvc = createMockMvc(controller);
	}

	@Test
	void calculateExpiration_whenTypeIsFixedDeposit() throws Exception {
		CalculateInvestmentRequest request = CalculateInvestmentRequest.builder()
			.type("예금")
			.amount("일시불 1000000")
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
}
