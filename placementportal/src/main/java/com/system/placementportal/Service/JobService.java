package com.system.placementportal.Service;

import com.system.placementportal.Dto.JobRequestDto;
import com.system.placementportal.Entity.Job;
import com.system.placementportal.Entity.Role;
import com.system.placementportal.Entity.User;
import com.system.placementportal.Exception.ResourceNotFoundException;
import com.system.placementportal.Repository.JobRepository;
import com.system.placementportal.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    // 🔹 Create Job
    public Job createJob(String email, JobRequestDto dto) {
        log.info("Admin {} creating a new job: {}", email, dto.getTitle());

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (user.getRole() != Role.ADMIN) {
            log.warn("Job creation failed: User {} is not ADMIN", email);
            throw new RuntimeException("Access denied");
        }

        Job job = Job.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .packageLpa(dto.getPackageLpa())
                .minCgpa(dto.getMinCgpa())
                .skillsRequired(dto.getSkillsRequired())
                .build();

        Job savedJob = jobRepository.save(job);
        log.info("Job created successfully with ID: {}", savedJob.getId());
        return savedJob;
    }
    
    // 🔹 Get All Jobs (Paginated)
    public Page<Job> getAllJobs(int page, int size, String sortBy) {
        log.info("Fetching jobs - Page: {}, Size: {}, SortBy: {}", page, size, sortBy);
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        return jobRepository.findAll(pageable);
    }

    // 🔹 Get Job by ID
    public Job getJobById(Long id) {
        log.info("Fetching job details for ID: {}", id);
        return jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with ID: " + id));
    }

    // 🔹 Delete Job
    public void deleteJob(Long id) {
        log.info("Deleting job with ID: {}", id);
        if (!jobRepository.existsById(id)) {
            throw new ResourceNotFoundException("Job not found with ID: " + id);
        }
        jobRepository.deleteById(id);
        log.info("Job deleted successfully with ID: {}", id);
    }
}