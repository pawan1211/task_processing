# task_processing

🚀 Task Processing System (Spring Boot + Kafka + Redis + JPA)

A real-world, Amazon-style asynchronous backend system built using Spring Boot, Kafka, Redis, JPA/Hibernate, and Multithreading.

This project demonstrates how modern backend systems:

Accept requests fast

Process tasks asynchronously

Scale under high load

Remain fault-tolerant and production-ready

📌 Why This Project?

Almost every serious backend system (Amazon, Flipkart, Uber, Netflix) follows this pattern:

Accept fast → Process later → Track status → Scale safely

🧠 Key Concepts Covered

REST API design

Asynchronous processing

Kafka producer & consumer

Kafka topics & consumer groups

Redis caching (fast status lookup)

JPA / Hibernate (database as source of truth)

Multithreading using ExecutorService

Production debugging (Kafka/Redis failures)

Clean layered architecture

🏗️ High Level Architecture
Client
  ↓
REST API (Spring Boot)
  ↓
Database (JPA / Hibernate)  ← Source of Truth
  ↓
Redis (Cache task status)
  ↓
Kafka (Async Queue)
  ↓
Worker (Kafka Consumer + Thread Pool)

🧩 Project Structure
task-processing-system
 ├── controller      # REST APIs
 ├── service         # Business logic
 ├── consumer        # Kafka consumers
 ├── config          # Kafka / Redis config
 ├── model           # JPA entities
 ├── repository      # JPA repositories
 └── TaskApplication.java

⚙️ Tech Stack
Technology	Purpose
Java 17	Language
Spring Boot	Application framework
Spring Kafka	Kafka integration
Kafka	Asynchronous messaging
Redis	Fast in-memory cache
JPA / Hibernate	Database persistence
MySQL	Relational database
ExecutorService	Multithreading
📦 Dependencies (pom.xml)

spring-boot-starter-web

spring-boot-starter-data-jpa

spring-kafka

spring-boot-starter-data-redis

postgresql

lombok


🔧 Application Properties

spring.application.name=Task-Processing-System

server.port=8080

# Kafka
spring.kafka.bootstrap-servers=localhost:9092
spring.kafka.consumer.group-id=task-group
spring.kafka.consumer.auto-offset-reset=earliest
spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer
spring.kafka.producer.value-serializer=org.apache.kafka.common.serialization.StringSerializer

# Redis
spring.redis.host=localhost
spring.redis.port=6379


# PostgreSQL DB connection
spring.datasource.url=jdbc:postgresql://localhost:5432/taskdb
spring.datasource.username=postgres
spring.datasource.password=
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA and Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect


▶️ How to Run (Local Setup)
1️⃣ Start Required Services
Kafka (Windows – Zookeeper mode)
zookeeper-server-start.bat config/zookeeper.properties
kafka-server-start.bat config/server.properties


Create topic:

kafka-topics.bat --create ^
--topic task-topic ^
--bootstrap-server localhost:9092 ^
--partitions 1 ^
--replication-factor 1

Redis
redis-server

MySQL
CREATE DATABASE taskdb;

2️⃣ Run Spring Boot App

From Eclipse:

Run → Spring Boot App

🧪 API Testing (Postman)
➤ Create Task
POST /tasks


Body:

"Generate monthly report"


Response:

<taskId>

➤ Get Task Status
GET /tasks/{taskId}


Response:

CREATED → PROCESSING → COMPLETED

🧵 Why Multithreading?

Kafka consumer threads must remain non-blocking.

We use:

ExecutorService executor = Executors.newFixedThreadPool(5);


Benefits:

Parallel task execution

Better CPU utilization

No Kafka lag

Controlled resource usage

🚨 Common  Errors & Fixes
Error 	 Cause	               Fix
Redis  ConnectionFailure	 Redis not running	Start Redis
Topic  not present	       Topic missing	Create topic
Kafka  Timeout	           Broker mismatch	Fix advertised.listeners
500    error	             Dependency downCheck logs
