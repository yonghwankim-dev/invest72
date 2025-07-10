package application.parser;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import application.request.CalculateInvestmentRequest;

public class JsonCalculateInvestmentRequestParser implements CalculateInvestmentRequestParser {

	private final ObjectMapper objectMapper;

	public JsonCalculateInvestmentRequestParser(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Override
	public CalculateInvestmentRequest parse(File file) {
		try {
			return objectMapper.readValue(file, CalculateInvestmentRequest.class);
		} catch (IOException e) {
			throw new IllegalArgumentException("Failed to parse JSON file: " + file.getAbsolutePath(), e);
		}
	}
}
