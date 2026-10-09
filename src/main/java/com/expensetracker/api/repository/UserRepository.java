package com.expensetracker.api.repository;

import com.expensetracker.api.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findByUsername(String username);

    List<User> findByActiveTrue();

    Optional<User> findByPhone(String phone);

    Optional<User> findByMail(String mail);

    Page<User> findByActiveTrue(Pageable pageable);
}
