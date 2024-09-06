package com.acm.casemanagement.steps;

import com.acm.casemanagement.dto.LoginDto;
import com.acm.casemanagement.dto.ResetPasswordDto;
import com.acm.casemanagement.dto.UserDto;
import com.acm.casemanagement.entity.User;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PasswordResetSteps {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CommonSteps commonSteps;

    private final String baseUrl = "http://localhost:8080/api/users";
    private ResponseEntity<String> response;
    private UserDto userDto;

    @Given("a user with username {string} and password {string} exists")
    public void aUserWithUsernameAndPasswordExists(String username, String password) {
        userDto = UserDto.builder()
                .username(username)
                .password(password)
                .firstname("John")
                .lastname("Doe")
                .email("john.doe@example.com")
                .isActive(true)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UserDto> entity = new HttpEntity<>(userDto, headers);
        restTemplate.postForEntity(baseUrl + "/register", entity, String.class);
    }

    @When("the user resets the password to {string} with old password {string}")
    public void theUserResetsThePasswordToWithOldPassword(String newPassword, String oldPassword) {
        ResetPasswordDto resetPasswordDto = ResetPasswordDto.builder()
                .username(userDto.getUsername())
                .oldPassword(oldPassword)
                .newPassword(newPassword)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ResetPasswordDto> entity = new HttpEntity<>(resetPasswordDto, headers);
        response = restTemplate.postForEntity(baseUrl + "/reset-password", entity, String.class);
        commonSteps.setResponse(response);
    }

    @Then("the password for username {string} should be {string}")
    public void thePasswordForUsernameShouldBe(String username, String newPassword) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        LoginDto loginDto = new LoginDto();
        loginDto.setUsername(username);
        loginDto.setPassword(newPassword);

        HttpEntity<LoginDto> loginEntity = new HttpEntity<>(loginDto, headers);
        ResponseEntity<User> loginResponse = restTemplate.postForEntity(baseUrl + "/login", loginEntity, User.class);

        assertEquals(HttpStatus.OK, loginResponse.getStatusCode());
        assertNotNull(loginResponse.getBody());
        assertEquals(username, loginResponse.getBody().getUsername());
    }
}
