package com.system.placementportal.Controller;

import com.system.placementportal.Dto.ChangePasswordRequestDto;
import com.system.placementportal.Dto.StudentUpdateRequestDto;
import com.system.placementportal.Entity.Student;
import com.system.placementportal.Service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(
        name = "Students",
        description = "Student Profile Management APIs"
)
@RequestMapping("/students")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    // 🔹 GET MY PROFILE
    @Operation(
            summary = "Get My Profile",
            description = "Retrieve profile details of the currently authenticated student"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Profile retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/me")
    public Student getMyProfile() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return studentService.getMyProfileByEmail(email);
    }

    // 🔹 UPDATE PROFILE (PATCH)
    @Operation(
            summary = "Update Profile",
            description = "Update profile information of the currently authenticated student"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Profile updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid profile data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PatchMapping("/profile")
    public Student updateProfile(@RequestBody StudentUpdateRequestDto request) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return studentService.updateProfileByEmail(email, request);
    }

    @Operation(
            summary = "Change Password",
            description = "Authenticated student can change account password"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Password changed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid password"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PatchMapping("/change-password")
    public String changePassword(
            @RequestBody @Valid ChangePasswordRequestDto request
    ) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return studentService.changePassword(email, request);
    }
}