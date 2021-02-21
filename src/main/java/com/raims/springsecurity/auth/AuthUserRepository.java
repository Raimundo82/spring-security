package com.raims.springsecurity.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, Integer> {

    @Query("SELECT a FROM AuthUser a WHERE a.username = ?1")
    public Optional<AuthUser> findUserByUsername(String username);
}
