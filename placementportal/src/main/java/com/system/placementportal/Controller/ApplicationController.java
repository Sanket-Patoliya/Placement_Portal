package com.system.placementportal.Controller;

import com.system.placementportal.Dto.ApplicationResponseDto;
import com.system.placementportal.Entity.Application;
import com.system.placementportal.Entity.Status;
import com.system.placementportal.Service.ApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(
        name = "Applications",
        description = "Job Application Management APIs"
)
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    // 🔹 STUDENT: Apply to Job
    @Operation(
            summary = "Apply for Job",
            description = "Authenticated student can apply for a specific job"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Application submitted successfully"),
            @ApiResponse(responseCode = "400", description = "Already applied or invalid request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Job not found")
    })
    @PostMapping("/apply/{jobId}")
    public ResponseEntity<String> apply(@PathVariable Long jobId) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return ResponseEntity.status(HttpStatus.CREATED).body(applicationService.applyWithEmail(email, jobId));
    }



    // 🔹 STUDENT: View My Applications
    @Operation(
            summary = "View My Applications",
            description = "Retrieve all applications submitted by the logged-in student"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Applications retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/me")
    public ResponseEntity<List<ApplicationResponseDto>> getMyApplications() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return ResponseEntity.ok(applicationService.getMyApplicationsByEmail(email));
    }



    // 🔹 ADMIN: Update Status
    @Operation(
            summary = "Update Application Status",
            description = "Admin can update application status to PENDING, APPROVED, or REJECTED"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Status updated successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "404", description = "Application not found")
    })
    @PatchMapping("/{id}/status")
    public ResponseEntity<ApplicationResponseDto> updateStatus(
            @PathVariable Long id,
            @RequestParam Status status
    ) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return ResponseEntity.ok(applicationService.updateStatus(email, id, status));
    }

    @Operation(
            summary = "Get Applications By Job",
            description = "Admin can view all applications submitted for a specific job"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Applications retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "404", description = "Job not found")
    })
    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<ApplicationResponseDto>> getApplicationsByJob(
            @PathVariable Long jobId
    ) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return ResponseEntity.ok(applicationService.getApplicationsByJob(email, jobId));
    }

    @Operation(
            summary = "Get All Applications",
            description = "Admin can view all applications in the system"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Applications retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @GetMapping
    public ResponseEntity<List<ApplicationResponseDto>> getAllApplications() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return ResponseEntity.ok(applicationService.getAllApplications(email));
    }
}