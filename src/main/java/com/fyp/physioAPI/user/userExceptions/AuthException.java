package com.fyp.physioAPI.user.userExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AuthException extends RuntimeException {

    public AuthException(String message) {
        super(message);
    }
}
