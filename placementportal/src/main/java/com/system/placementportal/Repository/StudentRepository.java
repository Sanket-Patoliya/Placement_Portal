package com.system.placementportal.Repository;

import com.system.placementportal.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface StudentRepository extends JpaRepository<Student,Long> {
    Optional<Student> findByUserId(Long userId);
}
