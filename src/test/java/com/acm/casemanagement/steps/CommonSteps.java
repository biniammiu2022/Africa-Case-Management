package com.acm.casemanagement.steps;

import com.fasterxml.jackson.databind.ObjectMapper;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CommonSteps {

    @Autowired
    private ObjectMapper objectMapper;

    @Setter
    private ResponseEntity<String> response;

    @Then("the response status should be {int}")
    public void theResponseStatusShouldBe(int status) {
        assertEquals(status, response.getStatusCode().value());
    }

    @And("the response should contain the user's id and username")
    public void theResponseShouldContainTheUserSIdAndUsername() {
        assertNotNull(response);
        Map<String, Object> responseBody = parseResponse(response.getBody());
        assertNotNull(responseBody.get("id"));
        assertNotNull(responseBody.get("username"));

    }

    @And("the response should contain an error message {string}")
    public void theResponseShouldContainAnErrorMessage(String message) {
        assertNotNull(response);
        Map<String, Object> responseBody = parseResponse(response.getBody());
        assertEquals(message, responseBody.get("message"));
    }

    private Map<String, Object> parseResponse(String responseBody) {
        try {
            return objectMapper.readValue(responseBody, HashMap.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse response", e);
        }
    }
}
