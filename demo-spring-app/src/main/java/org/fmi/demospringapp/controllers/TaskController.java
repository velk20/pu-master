package org.fmi.demospringapp.controllers;

import org.fmi.demospringapp.models.Task;
import org.fmi.demospringapp.models.TaskStatus;
import org.fmi.demospringapp.services.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Task getTask(@PathVariable String id) {
        Optional<Task> task = taskService.getById(id);
        if (task.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found");
        }

        return task.get();
    }

    @GetMapping
    @ResponseBody
    public List<Task> getAllTasks(
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(value = "sortBy", required = false) String sortBy
    ) {
        return taskService.getAllTasks(status, sortBy);
    }


    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public Task updateTask(@PathVariable String id,@RequestBody Task task) {
        Optional<Task> updatedTask = taskService.updateTask(id, task);
        if (updatedTask.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found");
        }

        return updatedTask.get();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable String id) {
        Optional<Task> task = taskService.getById(id);
        if (task.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found");
        }

        taskService.deleteTask(id);
    }
}
