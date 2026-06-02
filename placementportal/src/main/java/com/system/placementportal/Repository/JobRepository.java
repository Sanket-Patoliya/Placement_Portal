package com.system.placementportal.Repository;

import com.system.placementportal.Entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job,Long> {
}
