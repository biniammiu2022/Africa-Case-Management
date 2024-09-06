package com.acm.casemanagement.controller;
import com.acm.casemanagement.dto.ErrorResponse;
import com.acm.casemanagement.dto.LoginDto;
import com.acm.casemanagement.dto.ResetPasswordDto;
import com.acm.casemanagement.dto.UserDto;
import com.acm.casemanagement.entity.User;
import com.acm.casemanagement.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/users")
@Valid
public class UserController {

    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get all active users", description = "Returns a paginated list of active users only")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the users",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)) })
    })
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(Pageable pageable) {
        Page<User> usersPage = userService.getActiveUsers(pageable);
        List<User> users = usersPage.getContent();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @Operation(summary = "Get user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the user",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);

    }

    @Operation(summary = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input or user already exists",
                    content = @Content),

            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content),

    })
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody UserDto userDto) {
        log.info("Registering user: {}", userDto.getUsername());
        User createdUser = userService.registerUser(userDto);
        URI location = URI.create(String.format("/api/users/%s", createdUser.getId()));
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);
        return new ResponseEntity<>(createdUser, headers, HttpStatus.CREATED);
    }


    @Operation(summary = "Delete a user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deactivated successfully", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)),
                    @Content(mediaType = MediaType.APPLICATION_XML_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "User not found", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class)),
                    @Content(mediaType = MediaType.APPLICATION_XML_VALUE, schema = @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class)),
                            @Content(mediaType = MediaType.APPLICATION_XML_VALUE, schema = @Schema(implementation = ErrorResponse.class))
                    }),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class)),
                            @Content(mediaType = MediaType.APPLICATION_XML_VALUE, schema = @Schema(implementation = ErrorResponse.class))
                    })
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
        // inf log before deleting
        log.info("Received request to delete user with id: {}", id);
        User deletedUser = userService.deleteUserById(id);
        // info after deleting successfully
        log.info("User with id: {} deactivated successfully.", id);
        return new ResponseEntity<>(deletedUser, HttpStatus.NO_CONTENT);
    }
    @Operation(summary = "Login a user")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Login successful",
//                    content = {@Content(mediaType = "application/json",
//                            schema = @Schema(implementation = User.class))}),
//            @ApiResponse(responseCode = "401", description = "Invalid username or password",
//                    content = @Content)

    //  })

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = User.class)),
                            @Content(mediaType = "application/xml",
                                    schema = @Schema(implementation = User.class))
                    }),
            @ApiResponse(responseCode = "401", description = "Invalid username or password",
                    content = {
                            @Content(mediaType = "application/json"),
                            @Content(mediaType = "application/xml")
                    })
    })

    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@Valid @RequestBody LoginDto loginDto) {
        log.info("Logging in user: {}", loginDto.getUsername());
        User authenticatedUser = userService.loginUser(loginDto);
        return new ResponseEntity<>(authenticatedUser, HttpStatus.OK);
    }

    @Operation(summary = "Reset user password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password reset successful",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid current password", content = @Content)
    })
    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@Valid @RequestBody ResetPasswordDto resetPasswordDto) {
        log.info("Resetting password for user: {}", resetPasswordDto.getUsername());
        userService.resetPassword(resetPasswordDto);
        return ResponseEntity.ok().build();
    }
    @Operation(summary = "UPDATE a user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User Updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @PutMapping("/updateUser/{id}")
    public User updateUser(@PathVariable Long id, @Valid @RequestBody UserDto userDto) {
        log.info("updating a user started!");
        User aUser = userService.updateUserById(id,userDto);
        log.info("updating a user completed!");
        return aUser;

    }
}



