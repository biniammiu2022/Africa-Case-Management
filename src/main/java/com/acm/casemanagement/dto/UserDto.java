package com.acm.casemanagement.dto;



import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class UserDto {
    private Long id;
    @NotBlank(message = "First name is required")
    private String firstname;

    @NotBlank(message = "Last name is required")
    private String lastname;

    @NotBlank(message = "email name is required")
    @Email(message = "Invalid email format")
    @Column(nullable = false)
    private String email;

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Username must only contain alphanumeric characters")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern.List({
            @Pattern(regexp = "^(?=.*[0-9]).+$", message = "Password must contain at least one digit"),
            @Pattern(regexp = "^(?=.*[a-zA-Z]).+$", message = "Password must contain at least one letter")
    })
    private String password;
    @NotBlank(message = "Street is required")
    private String street;
    @NotBlank(message = "city is required")
    private String city;
    private String state;
    private String zipCode;
    @NotBlank(message = "country is required")
    private String country;


    private boolean isActive= true;

}
