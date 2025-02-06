package com.fyp.physioAPI.user;

import com.fyp.physioAPI.user.controller.UserController;
import com.fyp.physioAPI.user.model.UserModel;
import com.fyp.physioAPI.user.service.UserService;
import com.fyp.physioAPI.user.userExceptions.AuthException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.*;

@WebFluxTest(UserController.class)
public class UserControllerTest {

    private WebTestClient webTestClient;

    @MockitoBean
    private UserService userService;

    private final UserModel user = new UserModel(0L, "testFirstName", "testLastName", "test@email.com", "testPassword123");

    @BeforeEach
    public void setUp() {
        webTestClient = WebTestClient.bindToController(new UserController(userService)).build();
    }

    @Test
    void testRegisterUserSuccess() {
        when(userService.registerUser(any(UserModel.class))).thenReturn(Mono.empty());

        webTestClient.post()
                .uri("/api/auth/signup")
                .bodyValue(user)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(String.class)
                .isEqualTo("User registered successfully");
    }

    @Test
    void testRegisterUserFailure() {
        when(userService.registerUser(any(UserModel.class)))
                .thenReturn(Mono.error(new AuthException("User already exists")));

        webTestClient.post()
                .uri("/api/auth/signup")
                .bodyValue(user)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(String.class)
                .isEqualTo("User already exists");
    }

    @Test
    void testAuthenticateSuccess() {
        when(userService.validateUser(any(String.class),any(String.class))).thenReturn(Mono.empty());

        webTestClient.post()
                .uri("/api/auth/login")
                .bodyValue(user)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("User Logged in successfully");
    }

    @Test
    void testAuthenticateFailure() {
        when(userService.validateUser(any(String.class),any(String.class)))
                .thenReturn(Mono.error(new AuthException("Invalid credentials")));

        webTestClient.post()
                .uri("/api/auth/login")
                .bodyValue(user)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(String.class)
                .isEqualTo("Invalid credentials");
    }
}
