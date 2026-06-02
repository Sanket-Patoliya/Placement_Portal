package com.system.placementportal.Repository;

import com.system.placementportal.Entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    List<Application> findByStudentUserId(Long userId);

    boolean existsByStudentIdAndJobId(Long studentId, Long jobId);

    List<Application> findByJobId(Long jobId);
}