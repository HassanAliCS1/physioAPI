package com.fyp.physioAPI.user.controller;

import com.fyp.physioAPI.user.model.UserModel;
import com.fyp.physioAPI.user.service.UserService;
import com.fyp.physioAPI.user.userExceptions.AuthException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class UserController {

    private final UserService svc;

    @PostMapping("/signup")
    public Mono<ResponseEntity<String>> register(@RequestBody Mono<UserModel> user) {
        return user
                .flatMap(svc::registerUser)
                .then(Mono.just(ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully")))
                .onErrorResume(AuthException.class,
                        e -> Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage())));
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<String>> authenticate(@RequestBody Mono<UserModel> user) {
        return user
                .flatMap(u -> svc.validateUser(u.getEmail(),u.getPassword()))
                .then(Mono.just(ResponseEntity.status(HttpStatus.OK).body("User Logged in successfully")))
                .onErrorResume(AuthException.class,
                        e -> Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage())));

    }

    @PostMapping("/update")
    public Mono<ResponseEntity<String>> updateUser(@RequestBody Mono<UserModel> user) {
        return user
                .flatMap(svc::updateUser)
                .then(Mono.just(ResponseEntity.status(HttpStatus.OK).body("User updated successfully")))
                .onErrorResume(AuthException.class,
                        e -> Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage())));
    }

    @PostMapping("/delete")
    public Mono<ResponseEntity<String>> deleteUser(@RequestBody Mono<UserModel> user) {
        return user
                .flatMap(svc::deleteUser)
                .then(Mono.just(ResponseEntity.status(HttpStatus.OK).body("User deleted successfully")))
                .onErrorResume(AuthException.class,
                        e -> Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage())));
    }


}
