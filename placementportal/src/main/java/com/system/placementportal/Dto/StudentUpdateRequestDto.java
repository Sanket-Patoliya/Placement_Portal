package com.system.placementportal.Dto;

import lombok.Data;

@Data
public class StudentUpdateRequestDto {

    private String name;
    private String phone;
    private Double cgpa;
    private String skills;
    private String branch;
    private String resumeUrl;

}