package com.fyp.physioAPI.user.service;

import com.fyp.physioAPI.user.model.UserModel;
import com.fyp.physioAPI.user.userExceptions.AuthException;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<Void> validateUser(UserModel user) throws AuthException;

    Mono<Void> registerUser(UserModel user) throws AuthException;
}
