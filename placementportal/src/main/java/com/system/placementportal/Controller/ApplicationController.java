package com.system.placementportal.Controller;

import com.system.placementportal.Entity.Application;
import com.system.placementportal.Entity.Status;
import com.system.placementportal.Service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    // 🔹 STUDENT: Apply to Job
    @PostMapping("/apply/{jobId}")
    public String apply(@PathVariable Long jobId) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return applicationService.applyWithEmail(email, jobId);
    }

    // 🔹 STUDENT: View My Applications
    @GetMapping("/me")
    public List<Application> getMyApplications() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return applicationService.getMyApplicationsByEmail(email);
    }

    // 🔹 ADMIN: Update Status
    @PatchMapping("/{id}/status")
    public Application updateStatus(
            @PathVariable Long id,
            @RequestParam Status status
    ) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return applicationService.updateStatus(email, id, status);
    }
}