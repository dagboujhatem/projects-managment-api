package com.siga.restapi.service;

import com.siga.restapi.entities.Task;
import com.siga.restapi.payload.requests.CreateTaskRequest;
import com.siga.restapi.payload.requests.UpdateTaskRequest;

import java.util.List;

public interface TaskService {
    List<Task> findAll(int projectId);
    Task save(CreateTaskRequest createTaskRequest);
    Task findById(int id);
    Task update(int id, UpdateTaskRequest updateTaskRequest);
    void delete(int id);
}
