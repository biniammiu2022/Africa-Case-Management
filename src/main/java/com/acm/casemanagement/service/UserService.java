package com.acm.casemanagement.service;
import com.acm.casemanagement.dto.LoginDto;
import com.acm.casemanagement.dto.ResetPasswordDto;
import com.acm.casemanagement.dto.UserDto;
import com.acm.casemanagement.dto.UserValidator;
import com.acm.casemanagement.entity.User;
import com.acm.casemanagement.exception.UserException;
import com.acm.casemanagement.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class UserService {

    private UserRepository userRepository ;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(UserDto userDto) {
        UserValidator.validateUser(userDto);

        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            log.error("Username already taken: {}", userDto.getUsername());
            throw new UserException.UserAlreadyExistsException("Username already taken");
        }
        User user = User.builder()
                .email(userDto.getEmail())
                .firstname(userDto.getFirstname())
                .lastname(userDto.getLastname())
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .isActive(userDto.isActive())
                .build();
        User registeredUser = userRepository.save(user);
        log.info("User registered successfully: {}", registeredUser.getUsername());
        return registeredUser;
    }

    public User loginUser(LoginDto loginDto) {
        User user = userRepository.findByUsername(loginDto.getUsername())
                .orElseThrow(() -> new UserException.InvalidCredentialsException("Invalid username or password"));
        if (!loginDto.getPassword().equals(user.getPassword())) {
            throw new UserException.InvalidCredentialsException("Invalid username or password");
        }
        return user;
    }

    public User updateUserById(Long id, UserDto userDto) {
        return userRepository.findById(id).map(existingUser -> {
            existingUser.setEmail(userDto.getEmail());
            existingUser.setFirstname(userDto.getFirstname());
            existingUser.setLastname(userDto.getLastname());
            existingUser.setPassword(userDto.getPassword());
            existingUser.setUsername(userDto.getUsername());
            // Add more fields as needed
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new UserException.UserNotFoundException("User not found"));
    }

    public User getUserById(Long id) {
        return (userRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new UserException.UserNotFoundException("User not found or is inactive with id: " + id)));
    }


    public Page<User> getActiveUsers(Pageable pageable) {
        return userRepository.findAllByIsActiveTrue(pageable);
    }

    public User deleteUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserException.UserNotFoundException("User not found with id: " + id));
        if(!user.isActive()){
            throw new UserException.UserNotFoundException("User already deleted with id: " + id);
        }
        user.setActive(false);  // Mark user as inactive
        return userRepository.save(user);  // Save the updated user back to the repository
    }

    public void resetPassword(ResetPasswordDto resetPasswordDto) {
        User user = userRepository.findByUsername(resetPasswordDto.getUsername())
                .orElseThrow(() -> new UserException.UserNotFoundException("Invalid username"));

        if (!user.getPassword().equals(resetPasswordDto.getOldPassword())) {
            throw new UserException.InvalidCredentialsException("Invalid current password");
        }

        user.setPassword(resetPasswordDto.getNewPassword());
        userRepository.save(user);
        log.info("Successfully reset password for user: {}", resetPasswordDto.getUsername());

    }

}
