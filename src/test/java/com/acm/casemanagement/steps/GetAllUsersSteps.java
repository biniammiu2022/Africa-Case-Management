package com.acm.casemanagement.steps;

import com.acm.casemanagement.dto.UserDto;
import com.acm.casemanagement.entity.User;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class GetAllUsersSteps {

    @Autowired
    private RestTemplate restTemplate;
    private final String baseUrl = "http://localhost:8080/api/users";
    private ResponseEntity<List<User>> responseEntity;

    @Given("users exist in the system")
    public void usersExistInTheSystem() {
        // Add code to populate the database with users for testing
        UserDto userDto = UserDto.builder()
                .firstname("Feven")
                .lastname("Kiflai")
                .email("fev@gmail.com")
                .username("Fev")
                .password("Password123")  // Updated to meet validation criteria
                .isActive(true)
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UserDto> entity = new HttpEntity<>(userDto, headers);
        restTemplate.postForEntity(baseUrl + "/register", entity, String.class);
    }

    @When("a request is made to GET /users")
    public void aRequestIsMadeToGETUsers() {
        responseEntity = restTemplate.exchange(baseUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {
        });
    }

    @Then("the response should contain a list of users")
    public void theResponseShouldContainAListOfUsers() {
        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getBody());
        assertFalse(responseEntity.getBody().isEmpty());
    }

    @Given("no users exist in the system")
    public void noUsersExistInTheSystem() {
        // Assume the system starts with no users by default
    }

    @Then("the response should contain an empty list")
    public void theResponseShouldContainAnEmptyList() {
        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getBody());
        assertTrue(responseEntity.getBody().isEmpty());
    }
}
