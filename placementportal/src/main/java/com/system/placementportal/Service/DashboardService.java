package com.system.placementportal.Service;

import com.system.placementportal.Dto.DashboardStatsDto;
import com.system.placementportal.Entity.Role;
import com.system.placementportal.Repository.ApplicationRepository;
import com.system.placementportal.Repository.JobRepository;
import com.system.placementportal.Repository.StudentRepository;
import com.system.placementportal.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final StudentRepository studentRepository;
    private final JobRepository jobRepository;
    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;

    public DashboardStatsDto getStats() {

        return new DashboardStatsDto(
                studentRepository.count(),
                jobRepository.count(),
                applicationRepository.count(),
                userRepository.countByRole(Role.ADMIN)
        );
    }
}