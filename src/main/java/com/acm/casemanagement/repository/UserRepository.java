package com.acm.casemanagement.repository;

import com.acm.casemanagement.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByIdAndIsActiveTrue(Long id ); //just active
    Page<User> findAllByIsActiveTrue(Pageable pageable);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}


