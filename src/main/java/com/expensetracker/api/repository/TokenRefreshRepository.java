package com.expensetracker.api.repository;

import com.expensetracker.api.model.RefreshToken;
import com.expensetracker.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRefreshRepository extends JpaRepository<RefreshToken,Integer> {

    Optional<RefreshToken> findByTokenHash(String tokenHash);

    List<RefreshToken> findByUserAndRevokedFalse(User user);

    void deleteByUser(User user);
}