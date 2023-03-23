package com.example.projectmicroservice.repository;

import com.example.projectmicroservice.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
    List<Project> findByTitle(String title);
}
