/*
 * You can use the following import statements
 *
 * import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
 * 
 * import javax.persistence.*;
 * import java.util.List;
 * 
 */

// Write your code here
package com.example.findmyproject.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import com.example.findmyproject.model.Researcher;

@Entity
@Table(name = "project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int projectId;
    @Column(name = "name")
    private String projectName;
    @Column(name = "budget")
    private Double budget;
    @ManyToMany(mappedBy = "projects")
    @JsonIgnoreProperties("projects")
    private List<Researcher> researchers = new ArrayList<>();

    public Project() {

    }

    public Project(int projectId, String projectName, Double budget, List<Researcher> researchers) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.budget = budget;
        this.researchers = researchers;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getProjectId() {
        return this.projectId;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectName() {
        return this.projectName;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public Double getBudget() {
        return this.budget;
    }

    public void setResearchers(List<Researcher> researchers) {
        this.researchers = researchers;
    }

    public List<Researcher> getResearchers() {
        return this.researchers;
    }

}
