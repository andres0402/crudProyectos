package com.example.projectmicroservice.model;

import javax.persistence.*;


@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "project_description")
    private String project_description;

    @Column(name = "project_date")
    private String project_date;


    @Column(name = "user_id")
    private int user;

    public Project(String title, String project_description, String project_date) {
        this.title = title;
        this.project_description = project_description;
        this.project_date = project_date;
    }

    public Project(String title, String project_description, String project_date, int user) {
        this.title = title;
        this.project_description = project_description;
        this.project_date = project_date;
        this.user = user;
    }

    public Project() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProject_description() {
        return project_description;
    }

    public void setProject_description(String project_description) {
        this.project_description = project_description;
    }

    public String getProject_date() {
        return project_date;
    }

    public void setProject_date(String project_date) {
        this.project_date = project_date;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }
}
