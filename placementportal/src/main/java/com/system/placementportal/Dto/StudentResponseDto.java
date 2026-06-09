package com.system.placementportal.Dto;

import com.system.placementportal.Entity.Student;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentResponseDto {

    private Long id;
    private String name;
    private String phone;
    private Double cgpa;
    private String branch;
    private String skills;
    private String resumeUrl;
    private String resumeFileName;
    private String email;

    public static StudentResponseDto fromEntity(Student student) {
        if (student == null) {
            return null;
        }
        return StudentResponseDto.builder()
                .id(student.getId())
                .name(student.getName())
                .phone(student.getPhone())
                .cgpa(student.getCgpa())
                .branch(student.getBranch())
                .skills(student.getSkills())
                .resumeUrl(student.getResumeUrl())
                .resumeFileName(student.getResumeFileName())
                .email(student.getUser() != null ? student.getUser().getEmail() : null)
                .build();
    }
}
