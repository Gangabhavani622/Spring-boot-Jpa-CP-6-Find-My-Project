/*
 * You can use the following import statements
 *
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.http.HttpStatus;
 * import org.springframework.stereotype.Service;
 * import org.springframework.web.server.ResponseStatusException;
 * 
 * import java.util.*;
 *
 */

// Write your code here
package com.example.findmyproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import com.example.findmyproject.repository.*;
import com.sun.xml.bind.annotation.OverrideAnnotationOf;
import com.example.findmyproject.model.*;

@Service
public class ProjectJpaService implements ProjectRepository {
    @Autowired
    private ProjectJpaRepository projectJpaRepository;
    @Autowired
    private ResearcherJpaRepository researcherJpaRepository;

    @Override
    public ArrayList<Project> getProjectList() {
        List<Project> projectList = projectJpaRepository.findAll();
        ArrayList<Project> projects = new ArrayList<>(projectList);
        return projects;
    }

    @Override
    public Project addProject(Project project) {
        List<Integer> researcherIds = new ArrayList<>();
        for (Researcher researcher : project.getResearchers()) {

            researcherIds.add(researcher.getResearcherId());
        }
        List<Researcher> researchers = researcherJpaRepository.findAllById(researcherIds);
        if (researcherIds.size() != researchers.size()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "some researchers are not found");

        }
        project.setResearchers(researchers);
        for (Researcher researcher : researchers) {
            researcher.getProjects().add(project);
        }
        projectJpaRepository.save(project);
        researcherJpaRepository.saveAll(researchers);
        return project;

    }

    @Override
    public Project getProjectById(int projectId) {
        try {
            Project project = projectJpaRepository.findById(projectId).get();
            return project;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Project updateProject(int projectId, Project project) {
        try {
            Project newProject = projectJpaRepository.findById(projectId).get();
            if (project.getProjectName() != null) {
                newProject.setProjectName(project.getProjectName());
            }
            if (project.getBudget() != 0) {
                newProject.setBudget(project.getBudget());
            }
            if (project.getResearchers() != null) {
                List<Researcher> researchers = project.getResearchers();
                for (Researcher researcher : researchers) {
                    researcher.getProjects().remove(newProject);
                }
                List<Integer> researcherIds = new ArrayList<>();
                for (Researcher researcher : project.getResearchers()) {
                    researcherIds.add(researcher.getResearcherId());
                }
                List<Researcher> completeResearchers = researcherJpaRepository.findAllById(researcherIds);
                if (researcherIds.size() != completeResearchers.size()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "some researchers are not found");
                }
                newProject.setResearchers(completeResearchers);
                for (Researcher researcher : completeResearchers) {
                    researcher.getProjects().add(newProject);
                }
                researcherJpaRepository.saveAll(completeResearchers);
            }
            Project savedProject = projectJpaRepository.save(newProject);
            return savedProject;

        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }

    @Override
    public void deleteProject(int projectId) {
        try {
            Project project = projectJpaRepository.findById(projectId).get();
            List<Researcher> researchers = project.getResearchers();
            for (Researcher researcher : researchers) {
                researcher.getProjects().remove(project);
            }
            researcherJpaRepository.saveAll(researchers);
            projectJpaRepository.deleteById(projectId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @Override
    public List<Researcher> getProjectResearchers(int projectId) {
        try {
            Project project = projectJpaRepository.findById(projectId).get();
            List<Researcher> researchers = project.getResearchers();
            return researchers;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }

}