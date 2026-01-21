package com.example.Task.Processing.System.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Task.Processing.System.service.TaskService;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public String submitTask(@RequestBody String payload) {
        return taskService.createTask(payload);
    }

    @GetMapping("/{taskId}")
    public String getStatus(@PathVariable String taskId) {
        return taskService.getStatus(taskId);
    }
}
