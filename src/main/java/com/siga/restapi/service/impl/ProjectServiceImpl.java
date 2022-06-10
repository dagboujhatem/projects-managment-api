package com.siga.restapi.service.impl;

import com.siga.restapi.entities.Country;
import com.siga.restapi.entities.Project;
import com.siga.restapi.exceptions.ResourceNotFoundException;
import com.siga.restapi.payload.requests.CreateProjectRequest;
import com.siga.restapi.payload.requests.UpdateProjectRequest;
import com.siga.restapi.repositories.CountryRepository;
import com.siga.restapi.repositories.ProjectRepository;
import com.siga.restapi.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    CountryRepository countryRepository;

    @Override
    public List<Project> findAll() {
        return this.projectRepository.findAll();
    }

    @Override
    public Project save(CreateProjectRequest createProjectRequest) {
        Country pays = this.countryRepository.findById(createProjectRequest.getPays())
                .orElseThrow(() -> new ResourceNotFoundException("Pays non trouvé."));
        Project project = new Project();
        project.setCode(createProjectRequest.getCode());
        project.setDescription(createProjectRequest.getDescription());
        project.setState(createProjectRequest.getState());
        project.setPays(pays);
        project.setPrix(createProjectRequest.getPrix());
        project.setStartDate(createProjectRequest.getStartDate());
        project.setEndDate(createProjectRequest.getEndDate());
        return this.projectRepository.save(project);
    }

    @Override
    public Project findById(int id) {
        return this.projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Projet non trouvé."));
    }

    @Override
    public Project update(int id, UpdateProjectRequest updateProjectRequest) {
        Project oldProject = this.projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Projet non trouvé."));
        Country pays = this.countryRepository.findById(updateProjectRequest.getPays())
                .orElseThrow(() -> new ResourceNotFoundException("Pays non trouvé."));
        oldProject.setCode(updateProjectRequest.getCode());
        oldProject.setDescription(updateProjectRequest.getDescription());
        oldProject.setState(updateProjectRequest.getState());
        oldProject.setPays(pays);
        oldProject.setPrix(updateProjectRequest.getPrix());
        oldProject.setStartDate(updateProjectRequest.getStartDate());
        oldProject.setEndDate(updateProjectRequest.getEndDate());

        return this.projectRepository.save(oldProject);
    }

    @Override
    public void delete(int id) {
        this.projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Projet non trouvé."));
        this.projectRepository.deleteById(id);
    }
}
