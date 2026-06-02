package com.system.placementportal.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private Double packageLpa;
    private Double minCgpa;
    private String skillsRequired;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL)
    @JsonIgnore
    private java.util.List<Application> applications;
}
