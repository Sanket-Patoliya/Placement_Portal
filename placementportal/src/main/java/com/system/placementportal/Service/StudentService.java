package com.system.placementportal.Service;

import com.system.placementportal.Dto.StudentUpdateRequestDto;
import com.system.placementportal.Entity.Role;
import com.system.placementportal.Entity.Student;
import com.system.placementportal.Entity.User;
import com.system.placementportal.Repository.StudentRepository;
import com.system.placementportal.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;

    // 🔹 GET PROFILE
    public Student getMyProfileByEmail(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 🔥 Role check
        if (user.getRole() != Role.STUDENT) {
            throw new RuntimeException("Only students can access profile");
        }

        return studentRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    // 🔹 UPDATE PROFILE (PATCH)
    public Student updateProfileByEmail(String email, StudentUpdateRequestDto request) {

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

        return studentRepository.save(student);
    }
}