package com.system.placementportal.Dto;

import com.system.placementportal.Entity.Application;
import com.system.placementportal.Entity.Status;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationResponseDto {

    private Long id;
    private Status status;
    private Long jobId;
    private String jobTitle;
    private Long studentId;
    private String studentName;

    public static ApplicationResponseDto fromEntity(Application application) {
        if (application == null) {
            return null;
        }
        return ApplicationResponseDto.builder()
                .id(application.getId())
                .status(application.getStatus())
                .jobId(application.getJob() != null ? application.getJob().getId() : null)
                .jobTitle(application.getJob() != null ? application.getJob().getTitle() : null)
                .studentId(application.getStudent() != null ? application.getStudent().getId() : null)
                .studentName(application.getStudent() != null ? application.getStudent().getName() : null)
                .build();
    }
}
