package com.example.Task.Processing.System.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Task.Processing.System.model.TaskEntity;

public interface TaskRepository
extends JpaRepository<TaskEntity, String> {
}
