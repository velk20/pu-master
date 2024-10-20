package org.fmi.demospringapp.services;

import org.fmi.demospringapp.models.Task;
import org.fmi.demospringapp.models.TaskStatus;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService {
    private final Map<String, Task> tasksRepo = new HashMap<>();

    public Optional<Task> getById(String id) {
        return Optional.ofNullable(tasksRepo.getOrDefault(id, null));
    }

    public Task createTask(Task task) {
        String newId = UUID.randomUUID().toString();
        task.setId(newId);
        tasksRepo.put(newId, task);
        return task;
    }

    public List<Task> getAllTasks(TaskStatus status, String sortBy) {
        List<Task> allTasks = tasksRepo.values().stream().toList();

        if (status != null) {
            allTasks = allTasks.stream().filter(task -> task.getStatus() == status).toList();
        }

        if (sortBy != null) {
            allTasks = allTasks.stream().sorted(Comparator.comparing(Task::getDueDate)).toList();
        }

        return allTasks;
    }

    public Optional<Task> updateTask(String id, Task task) {
        Optional<Task> optionalTask = getById(id);
        if (optionalTask.isEmpty()){
            return optionalTask;
        }
        task.setId(id);
        tasksRepo.put(id, task);
        return Optional.of(task);
    }

    public void deleteTask(String id) {
        tasksRepo.remove(id);
    }
}
