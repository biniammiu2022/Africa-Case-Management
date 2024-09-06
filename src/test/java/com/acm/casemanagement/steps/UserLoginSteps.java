package com.acm.casemanagement.steps;


import com.acm.casemanagement.dto.LoginDto;
import com.acm.casemanagement.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.client.RestTemplate;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserLoginSteps {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CommonSteps commonSteps;
    private final String baseUrl = "http://localhost:8080/api/users";
    private ResponseEntity<String> response;
    private LoginDto loginDto;

    @Given("a registered user with username {string} and password {string}")
    public void aRegisteredUserWithUsernameAndPassword(String username, String password) {
        UserDto userDto = UserDto.builder()
                .firstname("Mulugeta")
                .lastname("Bisrat")
                .email("muller@gmail.com")
                .username(username)
                .password(password)
                .isActive(true).build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UserDto> entity = new HttpEntity<>(userDto, headers);
        restTemplate.postForEntity(baseUrl + "/register", entity, String.class);
    }

    @When("the user logs in with username {string} and password {string}")
    public void theUserLogsInWithUsernameAndPassword(String username, String password) {
        loginDto = LoginDto.builder()
                .password(password)
                .username(username)
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<LoginDto> entity = new HttpEntity<>(loginDto, headers);
        response = restTemplate.postForEntity(baseUrl + "/login", entity, String.class);
        commonSteps.setResponse(response);
    }
}

