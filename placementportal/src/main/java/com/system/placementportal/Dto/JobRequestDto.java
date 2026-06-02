package com.system.placementportal.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class JobRequestDto {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotNull(message = "Package is required")
    private Double packageLpa;

    @NotNull(message = "Minimum CGPA is required")
    private Double minCgpa;

    private String skillsRequired;
}