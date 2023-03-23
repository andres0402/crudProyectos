package com.example.projectmicroservice.controller;

import com.example.projectmicroservice.dto.Message;
import com.example.projectmicroservice.model.Project;
import com.example.projectmicroservice.model.UserProject;
import com.example.projectmicroservice.repository.UserProjectRepository;
import com.example.projectmicroservice.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ProjectController {
    @Autowired
    ProjectService projectService;

    @Autowired
    UserProjectRepository userProjectRepository;


    @GetMapping("/servicios/proyectos")
    public ResponseEntity<List<Project>> AllProjects(@RequestParam(required = false) String title) {
        try {
            List<Project> projects = new ArrayList<Project>();

            if (title == null)
                projectService.allProjects().forEach(projects::add);

            if (projects.isEmpty()) {
                return new ResponseEntity(new Message("No se encontraron proyectos"), HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(projects, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new Message("Se produjo un error al obtener los proyectos"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/usuario/{usuario_id}/proyectos")
    public ResponseEntity<List<Project>> getAllProjects(@PathVariable("usuario_id") int id) {
        try {
            List<Project> projects = new ArrayList<>();

            projectService.findAll(id).forEach(projects::add);

            if (projects.isEmpty()) {
                return new ResponseEntity(new Message("No hay proyectos registrados para este usuario"), HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(projects, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("Se produjo un error al obtener los proyectos", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/usuario/{usuario_id}/proyectos/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable("usuario_id") int user_id, @PathVariable("id") int id) {
        try {
            Optional<Project> projectData = projectService.findById(user_id, id);

            if (projectData.isPresent()) {
                return new ResponseEntity<>(projectData.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity(new Message("El proyecto no se encuentra registrado para este usuario"), HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception e) {
            return new ResponseEntity("Se produjo un error al obtener los proyectos", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/servicios/proyectos/{id}")
    public ResponseEntity<Project> getOneById(@PathVariable("id") int id) {
        Optional<Project> projectData = projectService.findOne(id);

        if (projectData.isPresent()) {
            return new ResponseEntity<>(projectData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity(new Message("El proyecto no se encuentra registrado"), HttpStatus.NOT_FOUND);
        }
    }



    @GetMapping("/usuario/{usuario_id}/proyectos/titulo/{titulo}")
    public ResponseEntity<List<Project>> getProjectByTitle(@PathVariable("usuario_id") int user_id, @PathVariable("titulo") String title) {
        List<Project> projectData = projectService.findByTitle(user_id, title);

        if (!projectData.isEmpty()) {
            return new ResponseEntity<>(projectData, HttpStatus.OK);
        } else {
            return new ResponseEntity(new Message("No hay proyectos registrados con este titulo"), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/servicios/proyectos/titulo/{title}")
    public ResponseEntity<List<Project>> getProjectTitle(@PathVariable("title") String title) {
        List<Project> projectData = projectService.findTitle(title);

        if (!projectData.isEmpty()) {
            return new ResponseEntity<>(projectData, HttpStatus.OK);
        } else {
            return new ResponseEntity(new Message("No hay proyectos registrados con este titulo"), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/usuario/{usuario_id}/proyectos/fecha/{fecha}")
    public ResponseEntity<List<Project>> getProjectByDate(@PathVariable("usuario_id") int user_id, @PathVariable("fecha") String project_date) {
        List<Project> projectData = projectService.findByProject_date(user_id, project_date);

        if (!projectData.isEmpty()) {
            return new ResponseEntity<>(projectData, HttpStatus.OK);
        } else {
            return new ResponseEntity(new Message("No hay proyectos registrados para esta fecha"), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/servicios/proyectos/fecha/{fecha}")
    public ResponseEntity<List<Project>> getByDate(@PathVariable("fecha") String project_date) {
        List<Project> projectData = projectService.findByDate(project_date);

        if (!projectData.isEmpty()) {
            return new ResponseEntity<>(projectData, HttpStatus.OK);
        } else {
            return new ResponseEntity(new Message("No hay proyectos registrados para esta fecha"), HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/servicios/proyectos/crear")
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        try {
            Project _project = projectService
                    .save(new Project(project.getTitle(), project.getProject_description(), project.getProject_date(), project.getUser()));
            return new ResponseEntity<>(_project, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(new Message("Se produjo un error al crear el proyectos"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/usuario/{usuario_id}/proyectos/crear")
    public ResponseEntity<Project> createProjectUser(@PathVariable("usuario_id") int user_id, @RequestBody Project project) {
        try {

            Project p = new Project(project.getTitle(), project.getProject_description(), project.getProject_date(), user_id);



            Project _project = projectService
                    .saveForUser(p);
            Optional <Project> pr = projectService.findOne(_project.getId());
            if(pr.isPresent()) {
                Project pro = pr.get();
                userProjectRepository.save(new UserProject(user_id, pro.getId()));
            }
            return new ResponseEntity<>(_project, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(new Message("No se pudo crear el proyecto"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/usuario/{usuario_id}/proyectos/{id}/editar")
    public ResponseEntity<Project> updateProject(@PathVariable("usuario_id") int user_id, @PathVariable("id") int id, @RequestBody Project project) {
        Optional<Project> projectData = projectService.findById(user_id, id);

        if (projectData.isPresent()) {
            Project _project = projectData.get();
            _project.setTitle(project.getTitle());
            _project.setProject_description(project.getProject_description());
            _project.setProject_date(project.getProject_date());
            _project.setUser(user_id);
            return new ResponseEntity<>(projectService.save(_project), HttpStatus.OK);
        } else {
            return new ResponseEntity(new Message("El proyecto a editar no se encuentra registrado"), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/servicios/proyectos/{id}/editar")
    public ResponseEntity<Project> updateProject(@PathVariable("id") int id, @RequestBody Project project) {
        Optional<Project> projectData = projectService.findOne(id);

        if (projectData.isPresent()) {
            Project _project = projectData.get();
            _project.setTitle(project.getTitle());
            _project.setProject_description(project.getProject_description());
            _project.setProject_date(project.getProject_date());
            if(project.getUser() == 0) {
                _project.setUser(_project.getUser());
            }
            else{
                _project.setUser(project.getUser());
            }
            return new ResponseEntity<>(projectService.save(_project), HttpStatus.OK);
        } else {
            return new ResponseEntity(new Message("El proyecto a editar no se encuentra registrado"), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/usuario/{usuario_id}/proyectos/{id}/eliminar")
    public ResponseEntity<HttpStatus> deleteProject(@PathVariable("usuario_id") int user_id, @PathVariable("id") int id) {
        try {
            projectService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity(new Message("Se produjo un error al eliminar el proyecto"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/servicios/proyectos/{id}/eliminar")
    public ResponseEntity<HttpStatus> deleteProject(@PathVariable("id") int id) {
        try {
            projectService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity(new Message("Se produjo un error al eliminar el proyecto"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/usuario/{usuario_id}/proyectos/eliminar")
    public ResponseEntity<HttpStatus> deleteAllProjects(@PathVariable("usuario_id") int user_id) {
        try {
            projectService.deleteAll(user_id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity(new Message("Se produjo un error al eliminar los proyectos"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/servicios/proyectos/eliminar")
    public ResponseEntity<HttpStatus> deleteAllProjects() {
        try {
            projectService.deleteAllProjects();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity(new Message("Se produjo un error al eliminar los proyectos"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
