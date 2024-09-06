package com.acm.casemanagement.exception;

import java.util.List;

public class UserException extends RuntimeException {

    public UserException(String message)
    {
        super(message);
    }

    public static class UserAlreadyExistsException extends UserException {
        public UserAlreadyExistsException(String message) {
            super(message);
        }
    }

    public static class InvalidCredentialsException extends UserException {
        public InvalidCredentialsException(String message) {
            super(message);
        }
    }

    public static class ResourceNotFoundException extends UserException {
        public ResourceNotFoundException(String message) {
            super(message);
        }
    }

    public static class UserNotFoundException extends UserException {
        public UserNotFoundException(String message) {
            super(message);
        }
    }

    public static class ValidationException extends UserException {
        public ValidationException(String message) {
            super(message);
        }

    }

    }


