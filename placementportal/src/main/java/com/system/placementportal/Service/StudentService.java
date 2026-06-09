package com.system.placementportal.Service;

import com.system.placementportal.Dto.ChangePasswordRequestDto;
import com.system.placementportal.Dto.StudentUpdateRequestDto;
import com.system.placementportal.Dto.StudentResponseDto;
import com.system.placementportal.Entity.Role;
import com.system.placementportal.Entity.Student;
import com.system.placementportal.Entity.User;
import com.system.placementportal.Exception.ResourceNotFoundException;
import com.system.placementportal.Repository.StudentRepository;
import com.system.placementportal.Repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ResumeService resumeService;

    // 🔹 GET PROFILE
    public StudentResponseDto getMyProfileByEmail(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 🔥 Role check
        if (user.getRole() != Role.STUDENT) {
            throw new RuntimeException("Only students can access profile");
        }

        Student student = studentRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        return StudentResponseDto.fromEntity(student);
    }

    // 🔹 UPDATE PROFILE (PATCH)
    public StudentResponseDto updateProfileByEmail(String email, StudentUpdateRequestDto request) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() != Role.STUDENT) {
            throw new RuntimeException("Only students can update profile");
        }

        Student student = studentRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // 🔥 PATCH LOGIC (only update non-null fields)

        if (request.getName() != null) {
            student.setName(request.getName());
        }

        if (request.getPhone() != null) {
            student.setPhone(request.getPhone());
        }

        if (request.getCgpa() != null) {
            student.setCgpa(request.getCgpa());
        }

        if (request.getBranch() != null) {
            student.setBranch(request.getBranch());
        }

        if (request.getSkills() != null) {
            student.setSkills(request.getSkills());
        }

        if (request.getResumeUrl() != null) {
            student.setResumeUrl(request.getResumeUrl());
        }

        return StudentResponseDto.fromEntity(studentRepository.save(student));
    }

    public String changePassword(
            String email,
            @Valid ChangePasswordRequestDto request
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        if (!passwordEncoder.matches(
                request.getOldPassword(),
                user.getPassword())) {

            throw new RuntimeException("Old password is incorrect");
        }

        user.setPassword(
                passwordEncoder.encode(request.getNewPassword()));

        userRepository.save(user);

        return "Password changed successfully";
    }

    public String uploadResume(
            String email,
            MultipartFile file
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        if (user.getRole() != Role.STUDENT) {
            throw new RuntimeException("Only students can upload resume");
        }

        Student student = studentRepository.findByUserId(user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student not found"));

        // 🔹 File Empty Check
        if (file.isEmpty()) {
            throw new RuntimeException("Please upload a file");
        }

        // 🔹 PDF Validation
        if (!"application/pdf".equals(file.getContentType())) {
            throw new RuntimeException("Only PDF files are allowed");
        }

        // 🔹 Size Validation (5 MB)
        if (file.getSize() > 5 * 1024 * 1024) {
            throw new RuntimeException("File size must be less than 5 MB");
        }

        String resumeUrl = resumeService.uploadResume(file);

        student.setResumeUrl(resumeUrl);
        student.setResumeFileName(file.getOriginalFilename());

        studentRepository.save(student);

        return resumeUrl;
    }}