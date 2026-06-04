package com.system.placementportal.Repository;

import com.system.placementportal.Entity.Role;
import com.system.placementportal.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    long countByRole(Role role);
}