package com.system.placementportal.Service;

import com.system.placementportal.Entity.*;
import com.system.placementportal.Exception.DuplicateResourceException;
import com.system.placementportal.Exception.ResourceNotFoundException;
import com.system.placementportal.Repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final StudentRepository studentRepository;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    // 🔹 APPLY TO JOB (STUDENT ONLY)
    public String applyWithEmail(String email, Long jobId) {
        log.info("Student {} applying for job ID: {}", email, jobId);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (user.getRole() != Role.STUDENT) {
            log.warn("Apply failed: User {} is not a STUDENT", email);
            throw new RuntimeException("Only students can apply");
        }

        Student student = studentRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Student profile not found"));

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found"));

        if (applicationRepository.existsByStudentIdAndJobId(student.getId(), jobId)) {
            log.warn("Apply failed: Student {} already applied for job ID {}", email, jobId);
            throw new DuplicateResourceException("You have already applied to this job");
        }

        Application application = Application.builder()
                .student(student)
                .job(job)
                .status(Status.APPLIED)
                .build();

        applicationRepository.save(application);
        log.info("Application successful for student {} and job ID {}", email, jobId);

        return "Applied successfully";
    }

    // 🔹 GET MY APPLICATIONS (STUDENT)
    public List<Application> getMyApplicationsByEmail(String email) {
        log.info("Fetching applications for student: {}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (user.getRole() != Role.STUDENT) {
            throw new RuntimeException("Only students can view applications");
        }

        return applicationRepository.findByStudentUserId(user.getId());
    }

    public List<Application> getApplicationsByJob(String email, Long jobId) {

        log.info("Fetching applications for job ID: {}", jobId);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (user.getRole() != Role.ADMIN) {
            throw new RuntimeException("Access denied");
        }

        if (!jobRepository.existsById(jobId)) {
            throw new ResourceNotFoundException("Job not found");
        }

        return applicationRepository.findByJobId(jobId);
    }
    // 🔹 UPDATE STATUS (ADMIN ONLY)
    public Application updateStatus(String email, Long applicationId, Status status) {
        log.info("Admin {} updating application ID {} to status {}", email, applicationId, status);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (user.getRole() != Role.ADMIN) {
            throw new RuntimeException("Access denied");
        }

        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found"));

        application.setStatus(status);

        return applicationRepository.save(application);
    }

    public List<Application> getAllApplications(String email) {

        log.info("Fetching all applications");

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (user.getRole() != Role.ADMIN) {
            throw new RuntimeException("Access denied");
        }

        return applicationRepository.findAll();
    }
}