package com.system.placementportal.Dto;

import com.system.placementportal.Entity.Job;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobResponseDto {

    private Long id;
    private String title;
    private String description;
    private Double packageLpa;
    private Double minCgpa;
    private String skillsRequired;

    public static JobResponseDto fromEntity(Job job) {
        if (job == null) {
            return null;
        }
        return JobResponseDto.builder()
                .id(job.getId())
                .title(job.getTitle())
                .description(job.getDescription())
                .packageLpa(job.getPackageLpa())
                .minCgpa(job.getMinCgpa())
                .skillsRequired(job.getSkillsRequired())
                .build();
    }
}
