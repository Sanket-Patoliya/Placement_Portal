package com.system.placementportal.Controller;

import com.system.placementportal.Dto.JobRequestDto;
import com.system.placementportal.Entity.Job;
import com.system.placementportal.Service.JobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @PostMapping
    public Job createJob(@RequestBody @Valid JobRequestDto dto) {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        return jobService.createJob(email,dto);
    }

    @GetMapping
    public Page<Job> getAllJobs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy) {
        return jobService.getAllJobs(page, size, sortBy);
    }

    @GetMapping("/{id}")
    public Job getJob(@PathVariable Long id) {
        return jobService.getJobById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
        return "Job deleted";
    }
}