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
    public Mono<Void> validateUser(String email, String password) throws AuthException {

        var optionalUser = userRepository.findByEmail(email).orElseThrow(() -> new AuthException("User not found"));

        return Mono.fromRunnable(() -> {
            if(!optionalUser.getPassword().equals(password)) {
                throw new AuthException("Email or password is incorrect");
            }
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

    @Override
    public Mono<Void> deleteUser(UserModel user) throws AuthException {
        var existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new AuthException("User not found with ID: " + user.getId()));

        return Mono.fromRunnable(() -> userRepository.delete(existingUser));
    }

    @Override
    public Mono<Void> updateUser(UserModel user) throws AuthException {

        var existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new AuthException("User not found with ID: " + user.getId()));

        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());

        return Mono.fromRunnable(() -> userRepository.save(existingUser));
    }

}
