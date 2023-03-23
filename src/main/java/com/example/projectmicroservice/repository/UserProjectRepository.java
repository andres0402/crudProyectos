package com.example.projectmicroservice.repository;

import com.example.projectmicroservice.model.UserProject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserProjectRepository extends JpaRepository<UserProject, Integer> {

}
