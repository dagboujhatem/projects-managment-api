package com.siga.restapi.controllers;

import com.siga.restapi.entities.Project;
import com.siga.restapi.exceptions.EmailAlreadyUsedException;
import com.siga.restapi.payload.requests.CreateProjectRequest;
import com.siga.restapi.payload.requests.UpdateProjectRequest;
import com.siga.restapi.payload.responses.ApiResponse;
import com.siga.restapi.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping
    public ApiResponse<Project> saveProject(@RequestBody CreateProjectRequest createProjectRequest) {
        return new ApiResponse<>(HttpStatus.OK.value(),
                "Le projet a été créé avec succès.",
                projectService.save(createProjectRequest));
    }

    @GetMapping
    public ApiResponse<List<Project>> listProject(){
        return new ApiResponse<>(HttpStatus.OK.value(),
                "La liste des projets a été récupérée avec succès.",
                projectService.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<Project> getOne(@PathVariable int id){
        return new ApiResponse<>(HttpStatus.OK.value(),
                "Les détails du projet ont été récupérés avec succès.",
                projectService.findById(id));
    }

    @PutMapping("/{id}")
    public ApiResponse<Project> update(@PathVariable int id, @RequestBody UpdateProjectRequest updateProjectRequest) throws EmailAlreadyUsedException {
        return new ApiResponse<>(HttpStatus.OK.value(),
                "Le projet a été mis à jour avec succès.",
                projectService.update(id, updateProjectRequest));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable int id) {
        projectService.delete(id);
        return new ApiResponse<>(HttpStatus.OK.value(),
                "Le projet a été supprimé avec succès.",
                null);
    }
}
