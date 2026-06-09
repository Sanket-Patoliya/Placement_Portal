package com.system.placementportal.Controller;

import com.system.placementportal.Dto.JobRequestDto;
import com.system.placementportal.Dto.JobResponseDto;
import com.system.placementportal.Entity.Job;
import com.system.placementportal.Service.JobService;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jobs")
@Tag(
        name = "Jobs",
        description = "Job Management APIs"
)
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @Operation(
            summary = "Create Job",
            description = "Create a new job posting. Accessible only to ADMIN users."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Job created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid job details"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @PostMapping
    public ResponseEntity<JobResponseDto> createJob(@RequestBody @Valid JobRequestDto dto) {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        return ResponseEntity.status(HttpStatus.CREATED).body(jobService.createJob(email,dto));
    }

    @Operation(
            summary = "Get All Jobs",
            description = "Retrieve a paginated list of all available job postings"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Jobs retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping
    public ResponseEntity<Page<JobResponseDto>> getAllJobs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {

        return ResponseEntity.ok(jobService.getAllJobs(page, size, sortBy));
    }


    @Operation(summary = "Get Job By ID",
            description = "Retrieve details of a specific job"
    )
    @GetMapping("/{id}")
    public ResponseEntity<JobResponseDto> getJob(@PathVariable Long id) {
        return ResponseEntity.ok(jobService.getJobById(id));
    }




    @Operation(
            summary = "Delete Job",
            description = "Delete an existing job posting. Accessible only to ADMIN users."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Job deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "404", description = "Job not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Update Job",
            description = "Update an existing job posting. Accessible only to ADMIN users."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Job updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid job details"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "404", description = "Job not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<JobResponseDto> updateJob(
            @PathVariable Long id,
            @RequestBody @Valid JobRequestDto dto
    ) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return ResponseEntity.ok(jobService.updateJob(email, id, dto));
    }
}