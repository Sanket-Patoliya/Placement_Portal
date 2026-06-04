package com.system.placementportal.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class DashboardStatsDto {

    private long totalStudents;
    private long totalJobs;
    private long totalApplications;
    private long totalAdmins;
}