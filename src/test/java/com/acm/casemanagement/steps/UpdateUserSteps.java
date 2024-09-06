
package com.acm.casemanagement.steps;
import com.acm.casemanagement.dto.UserDto;
import com.acm.casemanagement.entity.User;
import com.acm.casemanagement.repository.UserRepository;
import com.acm.casemanagement.service.UserService;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class UpdateUserSteps {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    User user,updateUser, registeredUser;
    UserDto userDto;


    @Given("a user exists with the user update details are: firstName {string}, lastName {string}, email {string}, username {string}, and password {string}")
    public void aUserExistsWithTheIdAndTheUserUpdateDetailsAre( String firstName, String lastName, String email, String username, String password) {
       user = User.builder()
                .username(username)
                .email(email)
                .isActive(true)
                .firstname(firstName)
                .lastname(lastName)
                .password(password)
                .build();
        registeredUser = userRepository.save(user);
    }

    @And("the user update details are:")
    public void the_user_update_details_are(Map<String, String> userDetails) {
        userDto = UserDto.builder()
                .firstname(userDetails.get("firstName"))
                .lastname(userDetails.get("lastName"))
                .email(userDetails.get("email"))
                .isActive(true)
                .username(userDetails.get("username"))
                .password(userDetails.get("password"))
                .build();
    }

    @When("a request is made to PUT \\/updateUser with updated details")
    public void a_request_is_made_to_PUT_users_with_updated_details() {
        updateUser = userService.updateUserById(registeredUser.getId(), userDto);
    }

    @Then("the user's details should be updated in the system")
    public void the_users_details_should_be_updated_in_the_system() {
       Assert.assertEquals(updateUser.getId(),registeredUser.getId());
       Assert.assertEquals(updateUser.getFirstname(),userDto.getFirstname());
    }
}