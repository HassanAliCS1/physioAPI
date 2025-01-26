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

    //TODO: function   -- purpose      -- service level logic
    //TODO: Login      -- (Read)      -> check DB
    //TODO: Signup     --(Create)     -> if conditions are met add to DB
    //TODO: UpdateInfo -- (Update)    -> if conditions are met Update
    //TODO: Delete     -- (Delete)    -> If conditions are met Delete

    private final UserService svc;

    @PostMapping("/signup")
    public Mono<ResponseEntity<String>> register(@RequestBody Mono<UserModel> user) {
        return user
                .flatMap(u -> svc.registerUser(u))
                .then(Mono.just(ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully")))
                .onErrorResume(AuthException.class,
                        e -> Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage())));
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<String>> authenticate(@RequestBody Mono<UserModel> user) {
        return user
                .flatMap(u -> svc.validateUser(u))
                .then(Mono.just(ResponseEntity.status(HttpStatus.OK).body("User Logged in successfully")))
                .onErrorResume(AuthException.class,
                        e -> Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage())));

    }


}
