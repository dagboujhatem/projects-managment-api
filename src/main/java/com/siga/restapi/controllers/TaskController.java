package com.siga.restapi.controllers;

import com.siga.restapi.entities.Task;
import com.siga.restapi.payload.requests.CreateTaskRequest;
import com.siga.restapi.payload.requests.UpdateTaskRequest;
import com.siga.restapi.payload.responses.ApiResponse;
import com.siga.restapi.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    TaskService taskService;

    @PostMapping
    public ApiResponse<Task> saveTask(@RequestBody CreateTaskRequest createTaskRequest) {
        return new ApiResponse<>(HttpStatus.OK.value(),
                "La tâche a été créé avec succès.",
                taskService.save(createTaskRequest));
    }

    @GetMapping("/all/{projectId}")
    public ApiResponse<List<Task>> listTasks(@PathVariable int projectId){
        return new ApiResponse<>(HttpStatus.OK.value(),
                "La liste des tâches a été récupérée avec succès.",
                taskService.findAll(projectId));
    }


    @GetMapping("/{id}")
    public ApiResponse<Task> getOne(@PathVariable int id){
        return new ApiResponse<>(HttpStatus.OK.value(),
                "Les détails du tâche ont été récupérés avec succès.",
                taskService.findById(id));
    }

    @PutMapping("/{id}")
    public ApiResponse<Task> update(@PathVariable int id, @RequestBody UpdateTaskRequest updateTaskRequest) {
        return new ApiResponse<>(HttpStatus.OK.value(),
                "La tâche a été mis à jour avec succès.",
                taskService.update(id, updateTaskRequest));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable int id) {
        taskService.delete(id);
        return new ApiResponse<>(HttpStatus.OK.value(),
                "Le tâche a été supprimé avec succès.",
                null);
    }
}
