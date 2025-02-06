package com.fyp.physioAPI.user.repository;

import com.fyp.physioAPI.user.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {

    Optional<UserModel> findById(Long id);

    Optional<UserModel> findByEmail(String email);
}
