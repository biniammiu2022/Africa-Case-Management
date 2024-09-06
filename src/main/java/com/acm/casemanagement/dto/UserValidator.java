    package com.acm.casemanagement.dto;
    import com.acm.casemanagement.exception.UserException;
    import org.apache.commons.lang3.StringUtils;

    import java.util.ArrayList;
    import java.util.List;

    public class UserValidator {

        public static void validateUser(UserDto userDto) {
            List<String> errors = new ArrayList<>();

            validateEmail(userDto.getEmail(), errors);
            validatePassword(userDto.getPassword(), errors);
            validateUsername(userDto.getUsername(), errors);
            validateFirstname(userDto.getFirstname(), errors);
            validateLastname(userDto.getLastname(), errors);

            if (!errors.isEmpty()) {
                throw new UserException.ValidationException(String.join(", ", errors));
            }
        }

        private static void validateEmail(String email, List<String> errors) {
            if (StringUtils.isBlank(email) || !email.matches("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
                errors.add("Invalid email format");
            }
        }

        private static void validatePassword(String password, List<String> errors) {
            if (StringUtils.isBlank(password) || password.length() < 8 || !password.matches("^(?=.*[0-9])(?=.*[a-zA-Z]).+$")) {
                errors.add("Password must be at least 8 characters long, contain at least one digit and one letter");
            }
        }

        private static void validateUsername(String username, List<String> errors) {
            if (StringUtils.isBlank(username) || username.length() < 3 || username.length() > 20 || !username.matches("^[a-zA-Z0-9]*$")) {
                errors.add("Username must be between 3 and 20 characters and contain only alphanumeric characters");
            }
        }

        private static void validateFirstname(String firstname, List<String> errors) {
            if (StringUtils.isBlank(firstname) || firstname.length() < 2 || firstname.length() > 50) {
                errors.add("Firstname must be between 2 and 50 characters");
            }
        }

        private static void validateLastname(String lastname, List<String> errors) {
            if (StringUtils.isBlank(lastname) || lastname.length() < 2 || lastname.length() > 50) {
                errors.add("Lastname must be between 2 and 50 characters");
            }
        }

    }