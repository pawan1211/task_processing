package com.example.Task.Processing.System.model;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tasks")
@Data                       // getters, setters, toString, equals, hashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class TaskEntity {

    @Id
    private String taskId;

    private String payload;

    private String status; // CREATED, PROCESSING, COMPLETED

    private Instant createdAt;
    private Instant updatedAt;

    // getters & setters
}
