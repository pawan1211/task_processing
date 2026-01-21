package com.example.Task.Processing.System.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.Task.Processing.System.model.TaskEntity;
import com.example.Task.Processing.System.repo.TaskRepository;

@Service
public class TaskService {

    private final TaskRepository repository;
    private final RedisTemplate<String, String> redisTemplate;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public TaskService(TaskRepository repository,
                       RedisTemplate<String, String> redisTemplate,
                       KafkaTemplate<String, String> kafkaTemplate) {
        this.repository = repository;
        this.redisTemplate = redisTemplate;
        this.kafkaTemplate = kafkaTemplate;
    }

    public String createTask(String payload) {
        String taskId = UUID.randomUUID().toString();

        TaskEntity task = new TaskEntity();
        task.setTaskId(taskId);
        task.setPayload(payload);
        task.setStatus("CREATED");
        task.setCreatedAt(Instant.now());

        // 1️⃣ Save permanently (DB)
        repository.save(task);

        // 2️⃣ Cache for fast reads
        redisTemplate.opsForValue().set(taskId, "CREATED");

        // 3️⃣ Send async task
        kafkaTemplate.send("task-topic", taskId);

        return taskId;
    }

    public String getStatus(String taskId) {

        // 1️⃣ Redis first (FAST)
        String status = redisTemplate.opsForValue().get(taskId);
        if (status != null) return status;

        // 2️⃣ DB fallback (SAFE)
        return repository.findById(taskId)
                .map(TaskEntity::getStatus)
                .orElse("NOT_FOUND");
    }
}
