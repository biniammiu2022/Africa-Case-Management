package com.acm.casemanagement.steps;

import com.acm.casemanagement.dto.UserDto;
import com.acm.casemanagement.entity.User;
import com.acm.casemanagement.repository.UserRepository;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.client.RestTemplate;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserGetByIdSteps {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    private final String baseUrl = "http://localhost:8080/api/users";
    private ResponseEntity<UserDto> response;
    private User aUser;
    private ResponseEntity<User> responseEntity;

    @Given("a user exists with the ID")
    public void aUserExistsWithTheID() {
        UserDto userDto = UserDto.builder()
                .firstname("frena")
                .lastname("mhretab")
                .email("frenamhretab@gmail.com")
                .username("fre")
                .password("124")
                .isActive(true).build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UserDto> entity = new HttpEntity<>(userDto, headers);
         responseEntity=restTemplate.postForEntity(baseUrl + "/register", entity, User.class);
    }

    @When("a request is made to GET /users by id") //
    public void aRequestIsMadeToGETUsersId() {
        String url = baseUrl + "/" + responseEntity.getBody().getId();
        aUser = restTemplate.getForObject(url, User.class);
        }

        @Then("the response should contain the user's details including ID, name, and email") //
        public void theResponseShouldContainTheUserSDetailsIncludingIDNameAndEmail () {
            assertNotNull(aUser);
        }
    }





