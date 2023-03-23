package com.example.projectmicroservice.model;

import javax.persistence.*;

@Entity
@Table(name = "usuarioproyecto")
public class UserProject {
    @Id
    private int project_id;

    @Column(name = "user_id")
    private int user_id;

    public UserProject() {
    }

    public UserProject(int user_id, int project_id) {
        this.user_id = user_id;
        this.project_id = project_id;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
