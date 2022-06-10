package com.siga.restapi.service.impl;

import com.siga.restapi.entities.Project;
import com.siga.restapi.entities.Task;
import com.siga.restapi.exceptions.ResourceNotFoundException;
import com.siga.restapi.payload.requests.CreateTaskRequest;
import com.siga.restapi.payload.requests.UpdateTaskRequest;
import com.siga.restapi.repositories.ProjectRepository;
import com.siga.restapi.repositories.TaskRepository;
import com.siga.restapi.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Override
    public List<Task> findAll(int projectId) {
        Project project = this.projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Projet non trouvé."));
        return project.getTasks();
    }

    @Override
    public Task save(CreateTaskRequest createTaskRequest) {
        Project project = this.projectRepository.findById(createTaskRequest.getProject())
                .orElseThrow(() -> new ResourceNotFoundException("Projet non trouvé."));
        Task createdTask = new Task();
        createdTask.setCode(createTaskRequest.getCode());
        createdTask.setLibelle(createTaskRequest.getLibelle());
        createdTask.setProject(project);
        return this.taskRepository.save(createdTask);
    }

    @Override
    public Task findById(int id) {
        return this.taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tâche non trouvé."));
    }

    @Override
    public Task update(int id, UpdateTaskRequest updateTaskRequest) {
        Task oldTask = this.taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tâche non trouvé."));
        oldTask.setCode(updateTaskRequest.getCode());
        oldTask.setLibelle(updateTaskRequest.getLibelle());
        return this.taskRepository.save(oldTask);
    }

    @Override
    public void delete(int id) {
        this.taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tâche non trouvé."));
        this.taskRepository.deleteById(id);
    }
}
