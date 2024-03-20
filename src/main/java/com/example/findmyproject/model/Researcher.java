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

import com.example.findmyproject.model.Project;

@Entity
@Table(name = "researcher")
public class Researcher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int researcherId;
    @Column(name = "name")
    private String researcherName;
    @Column(name = "specialization")
    private String specialization;
    @ManyToMany
    @JoinTable(name = "researcher_project", joinColumns = @JoinColumn(name = "researcherid"), inverseJoinColumns = @JoinColumn(name = "projectid"))
    @JsonIgnoreProperties("researchers")
    private List<Project> projects = new ArrayList<>();

    public Researcher() {

    }

    public Researcher(int researcherId, String researcherName, String specialization, List<Project> projects) {
        this.researcherId = researcherId;
        this.researcherName = researcherName;
        this.specialization = specialization;
        this.projects = projects;
    }

    public void setResearcherId(int researcherId) {
        this.researcherId = researcherId;
    }

    public int getResearcherId() {
        return this.researcherId;
    }

    public void setResearcherName(String researcherName) {
        this.researcherName = researcherName;
    }

    public String getResearcherName() {
        return this.researcherName;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getSpecialization() {
        return this.specialization;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public List<Project> getProjects() {
        return this.projects;
    }

}
