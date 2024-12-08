package com.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.entity.Task;

public interface TaskRepository extends JpaRepository<Task,Integer> {
}
