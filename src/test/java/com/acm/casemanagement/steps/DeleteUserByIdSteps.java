package com.acm.casemanagement.steps;

import com.acm.casemanagement.entity.User;
import com.acm.casemanagement.exception.UserException;
import com.acm.casemanagement.repository.UserRepository;
import com.acm.casemanagement.service.UserService;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class DeleteUserByIdSteps {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private Throwable exception;

    // Scenario: Successfully delete an existing user by ID
    @Given("a user exists with ID {long} and is active")
    public void aUserExistsWithIdAndIsActive(Long id) {
        User user = User.builder()
                .id(id)
                .username("Test User")
                .email("test@example.com")
                .isActive(true)
                .firstname("Firstname")
                .lastname("Lastname")
                .password("Password123")  // Updated to meet validation criteria
                .build();

        userRepository.save(user);
    }

    @When("I delete the user with ID {long}")
    public void iDeleteTheUserWithId(Long id) {
        try {
            userService.deleteUserById(id);
        } catch (Throwable e) {
            exception = e;
        }
    }

    @Then("the user with ID {long} should be marked as inactive")
    public void theUserWithIdShouldBeMarkedAsInactive(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserException.UserNotFoundException("User not found with id: " + id));
        assertFalse(user.isActive());
    }

    // Scenario: Attempt to delete a non-existing user
    @When("I attempt to delete a non-existing user with ID {long}")
    public void iAttemptToDeleteANonExistingUserWithId(Long id) {
        try {
            userService.deleteUserById(id);
        } catch (Throwable e) {
            exception = e;
        }
    }

    @Then("I should receive a {string} error")
    public void iShouldReceiveAnError(String errorMessage) {
        assertNotNull(exception);
        assertTrue(exception instanceof UserException.UserNotFoundException);
        assertEquals(errorMessage, exception.getMessage());
    }
}
