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
import com.example.findmyproject.model.*;

@Service
public class ResearcherJpaService implements ResearcherRepository {
    @Autowired
    private ProjectJpaRepository projectJpaRepository;
    @Autowired
    private ResearcherJpaRepository researcherJpaRepository;

    @Override
    public ArrayList<Researcher> getResearcherList() {
        List<Researcher> researcherList = researcherJpaRepository.findAll();
        ArrayList<Researcher> researchers = new ArrayList<>(researcherList);
        return researchers;
    }

    @Override
    public Researcher addResearcher(Researcher researcher) {
        List<Integer> projectIds = new ArrayList<>();
        for (Project project : researcher.getProjects()) {
            projectIds.add(project.getProjectId());

        }

        List<Project> projects = projectJpaRepository.findAllById(projectIds);
        if (projectIds.size() != projects.size()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "some projects are not found");
        }
        researcher.setProjects(projects);
        for (Project project : projects) {
            project.getResearchers().add(researcher);
        }
        researcherJpaRepository.save(researcher);
        projectJpaRepository.saveAll(projects);
        return researcher;

    }

    @Override
    public Researcher getResearcherById(int researcherId) {
        try {
            Researcher researcher = researcherJpaRepository.findById(researcherId).get();
            return researcher;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public Researcher updateResearcher(int researcherId, Researcher researcher) {
        try {
            Researcher newResearcher = researcherJpaRepository.findById(researcherId).get();
            if (researcher.getResearcherName() != null) {
                newResearcher.setResearcherName(researcher.getResearcherName());
            }
            if (researcher.getSpecialization() != null) {
                newResearcher.setSpecialization(researcher.getSpecialization());
            }
            if (researcher.getProjects() != null) {
                List<Project> projects = researcher.getProjects();
                for (Project project : projects) {
                    project.getResearchers().remove(newResearcher);
                }
                List<Integer> projectIds = new ArrayList<>();
                for (Project project : projects) {
                    projectIds.add(project.getProjectId());
                }
                List<Project> completeProjects = projectJpaRepository.findAllById(projectIds);
                if (projectIds.size() != completeProjects.size()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "some projects are not found");
                }
                newResearcher.setProjects(completeProjects);
                for (Project project : completeProjects) {
                    project.getResearchers().add(newResearcher);
                }
                projectJpaRepository.saveAll(completeProjects);
            }
            researcherJpaRepository.save(newResearcher);
            return newResearcher;

        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }

    @Override
    public void deleteResearcher(int researcherId) {
        try {
            Researcher researcher = researcherJpaRepository.findById(researcherId).get();
            List<Project> projects = researcher.getProjects();
            for (Project project : projects) {
                project.getResearchers().remove(researcher);
            }
            projectJpaRepository.saveAll(projects);
            researcherJpaRepository.deleteById(researcherId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @Override
    public List<Project> getResearcherProjects(int researcherId) {
        try {
            Researcher researcher = researcherJpaRepository.findById(researcherId).get();
            List<Project> projects = researcher.getProjects();
            return projects;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }

}