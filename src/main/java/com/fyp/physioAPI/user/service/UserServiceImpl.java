package com.fyp.physioAPI.user.service;

import com.fyp.physioAPI.user.model.UserModel;
import com.fyp.physioAPI.user.repository.UserRepository;
import com.fyp.physioAPI.user.userExceptions.AuthException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public Mono<Void> validateUser(UserModel user) throws AuthException {
        return Mono.fromRunnable(() -> {
            userRepository
                    .findByEmailAndPassword(user.getEmail(), user.getPassword())
                    .orElseThrow(() -> new AuthException("Email or password is incorrect"));
        });


    }

    @Override
    public Mono<Void> registerUser(UserModel user) throws AuthException {
        return Mono.fromRunnable(() -> {
            NewUserValidator.validateEmail(user.getEmail());
            NewUserValidator.validatePassword(user.getPassword());
            NewUserValidator.validateName(user.getFirstName(), "first_name");
            NewUserValidator.validateName(user.getLastName(), "last_name");

            userRepository.save(user);
        });
    }
}
