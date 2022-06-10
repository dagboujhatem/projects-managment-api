package com.siga.restapi.service;

import com.siga.restapi.entities.Project;
import com.siga.restapi.payload.requests.CreateProjectRequest;
import com.siga.restapi.payload.requests.UpdateProjectRequest;

import java.util.List;

public interface ProjectService {
    List<Project> findAll();
    Project save(CreateProjectRequest createProjectRequest);
    Project findById(int id);
    Project update(int id, UpdateProjectRequest updateProjectRequest);
    void delete(int id);
}
