package com.example.projectmicroservice.service;

import com.example.projectmicroservice.model.Project;
import com.example.projectmicroservice.model.UserProject;
import com.example.projectmicroservice.repository.ProjectRepository;
import com.example.projectmicroservice.repository.UserProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    UserProjectRepository userProjectRepository;

    public List<Project> allProjects(){
        return (List<Project>) projectRepository.findAll();
    }
    public List<Project> findAll(int id){
        List<Project> projects = (List<Project>) projectRepository.findAll(), results = new ArrayList<>();
        List<UserProject> users = userProjectRepository.findAll(), res = new ArrayList<>();

        for (UserProject u: users){
            if(u.getUser_id() == id){
                res.add(u);
            }
        }
        for (UserProject u: res){
            for(Project p: projects){
                if(u.getProject_id() == p.getId()){
                    results.add(p);
                }
            }
        }
        return results;
    }

    public Optional<Project> findOne(int id){
        return projectRepository.findById(id);
    }

    public Optional<Project> findById(int user_id, int id){
        List<Project> projects = this.findAll(user_id);
        for(Project p: projects){
            if(p.getId() == id){
                return projectRepository.findById(id);
            }
        }
        return null;
    }

    public List<Project> findByTitle(int user_id, String title){
        List<Project> projects = this.findAll(user_id), results = new ArrayList<>();

        for (Project p: projects){
            if(p.getTitle().equals(title)){
                results.add(p);
            }
        }
        return results;
    }

    public List<Project> findTitle(String title){
        return projectRepository.findByTitle(title);
    }

    public List<Project> findByProject_date(int user_id, String project_date){
        List<Project> projects = this.findAll(user_id), results = new ArrayList<>();

        for (Project p:projects){
            if(p.getProject_date().equals(project_date)){
                results.add(p);
            }
        }
        return results;
    }

    public List<Project> findByDate(String project_date){
        List<Project> projects = projectRepository.findAll(), results = new ArrayList<>();
        for (Project p: projects){
            if(p.getProject_date().equals(project_date)){
                results.add(p);
            }
        }
        return results;
    }

    public boolean existsById(int id){
        return projectRepository.existsById(id);
    }


    public Project save (Project project){
        return projectRepository.save(project);
    }

    public Project saveForUser (Project project){
        List<Project> projects = this.findAll(project.getUser());
        for (Project p:projects){
            if(p.getId() == project.getId()){
                return null;
            }
        }
        return projectRepository.save(project);

    }


    public void deleteById (int id){
        userProjectRepository.deleteById(id);
        projectRepository.deleteById(id);
    }


    public void deleteAll (int user_id){
        List<Project> projects = this.findAll(user_id);
        for(Project p: projects){
            userProjectRepository.deleteById(p.getId());
            projectRepository.deleteById(p.getId());
        }
    }

    public void deleteAllProjects (){
        projectRepository.deleteAll();
    }






}
