package com.system.placementportal.Controller;

import com.system.placementportal.Dto.DashboardStatsDto;
import com.system.placementportal.Service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(
        name = "Dashboard",
        description = "Admin Dashboard APIs"
)
public class DashboardController {

    private final DashboardService dashboardService;

    @Operation(
            summary = "Get Dashboard Statistics",
            description = "Returns total students, jobs and applications"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Statistics fetched successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Access Denied")
    })
    @GetMapping("/stats")
    public ResponseEntity<DashboardStatsDto> getStats() {
        return ResponseEntity.ok(dashboardService.getStats());
    }
}