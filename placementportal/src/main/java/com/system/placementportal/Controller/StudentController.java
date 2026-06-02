package com.system.placementportal.Controller;

import com.system.placementportal.Dto.StudentUpdateRequestDto;
import com.system.placementportal.Entity.Student;
import com.system.placementportal.Service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    // 🔹 GET MY PROFILE
    @GetMapping("/me")
    public Student getMyProfile() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return studentService.getMyProfileByEmail(email);
    }

    // 🔹 UPDATE PROFILE (PATCH)
    @PatchMapping("/profile")
    public Student updateProfile(@RequestBody StudentUpdateRequestDto request) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return studentService.updateProfileByEmail(email, request);
    }
}