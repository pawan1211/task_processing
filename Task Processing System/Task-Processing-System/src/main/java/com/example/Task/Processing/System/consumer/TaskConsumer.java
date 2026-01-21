package com.example.Task.Processing.System.consumer;

import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.Task.Processing.System.model.TaskEntity;
import com.example.Task.Processing.System.repo.TaskRepository;

@Component
public class TaskConsumer {

    private final TaskRepository repository;
    private final RedisTemplate<String, String> redisTemplate;

    // 🔥 MULTITHREADING (CONTROLLED)
    private final ExecutorService executor =
            Executors.newFixedThreadPool(5);

    public TaskConsumer(TaskRepository repository,
                        RedisTemplate<String, String> redisTemplate) {
        this.repository = repository;
        this.redisTemplate = redisTemplate;
    }

    @KafkaListener(topics = "task-topic", groupId = "task-group")
    public void consume(String taskId) {

        // Kafka thread stays FAST
        executor.submit(() -> processTask(taskId));
    }

    private void processTask(String taskId) {

        // ========================
        // PROCESSING
        // ========================
      redisTemplate.opsForValue().set(taskId, "PROCESSING");

        TaskEntity task = repository.findById(taskId).orElseThrow();
        task.setStatus("PROCESSING");
        task.setUpdatedAt(Instant.now());
        repository.save(task);

        // Simulate heavy work
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ignored) {}

        // ========================
        // COMPLETED
        // ========================
      redisTemplate.opsForValue().set(taskId, "COMPLETED");

        task.setStatus("COMPLETED");
        task.setUpdatedAt(Instant.now());
        repository.save(task);
    }
}
