package org.muzhevsky.projects.controllers;

import org.muzhevsky.authorization.exceptions.ExpiredTokenException;
import org.muzhevsky.authorization.exceptions.TokenNotFoundException;
import org.muzhevsky.authorization.services.AuthorizationService;
import org.muzhevsky.projects.dtos.DeleteProjectData;
import org.muzhevsky.projects.dtos.ProjectForm;
import org.muzhevsky.projects.exceptions.ProjectNotFoundException;
import org.muzhevsky.projects.models.ProjectModel;
import org.muzhevsky.projects.services.ProjectService;
import org.muzhevsky.projects.services.ProjectManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProjectController {
    @Autowired
    ProjectManagementService projectManagementService;
    @Autowired
    ProjectService projectService;

    @Autowired
    @Qualifier("authorizationService")
    AuthorizationService authorizationService;


    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("projects/folder/{id}")
    public ResponseEntity<List<ProjectModel>> fetchProjectsFromFolder(@PathVariable("id") Integer id,
                                                                      @RequestHeader("accessToken") String token) {
        try {
            var tokenData = authorizationService.getTokenData(token);
            if (tokenData.isExpired()) throw new ExpiredTokenException();

            var accountId = tokenData.getAccountId();
            return new ResponseEntity<>(projectManagementService.getProjects(id, accountId), HttpStatus.OK);
        }
        catch (ExpiredTokenException ex) {
            System.out.println(ex.getMessage());
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("projects/create")
    public ResponseEntity<List<ProjectModel>> createProject(@RequestBody ProjectForm form) {
        try {
            var tokenData = authorizationService.getTokenData(form.getAccessToken());
            if (tokenData.isExpired()) throw new ExpiredTokenException();

            var accountId = tokenData.getAccountId();
            projectManagementService.createProject(form, accountId);

            var projects = projectManagementService.getProjects(form.getFolderId(), accountId);
            return new ResponseEntity<>(projects, HttpStatus.OK);
        }
        catch (ExpiredTokenException ex) {
            System.out.println(ex.getMessage());
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("projects/{id}")
    public ResponseEntity<ProjectModel> fetchProjectById(@PathVariable("id") Integer id,
                                                         @RequestHeader("accessToken") String token) {
        try {
            var tokenData = authorizationService.getTokenData(token);
            if (tokenData.isExpired()) throw new ExpiredTokenException();

            return new ResponseEntity<>(projectService.getProjects(id), HttpStatus.OK);
        } catch (ProjectNotFoundException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        catch (ExpiredTokenException ex) {
            System.out.println(ex.getMessage());
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("projects/isowner/{id}")
    public ResponseEntity<Boolean> checkIsOwner(@RequestHeader(value = "accessToken") String accessToken,
                                                       @PathVariable(value = "id") int projectId) {
        try {
            var tokenData = authorizationService.getTokenData(accessToken);
            if (tokenData.isExpired()) throw new ExpiredTokenException();

            var accountId = tokenData.getAccountId();
            return new ResponseEntity(projectService.checkOwner(accountId, projectId), HttpStatus.OK);
        }
        catch (TokenNotFoundException e) {
            return new ResponseEntity(false, HttpStatus.OK);
        }
        catch (ExpiredTokenException ex) {
            System.out.println(ex.getMessage());
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
            return new ResponseEntity(false, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("projects/delete")
    public ResponseEntity<String> deleteProject(@RequestBody DeleteProjectData data){
        try {
            var tokenData = authorizationService.getTokenData(data.getAccessToken());
            if (tokenData.isExpired()) throw new ExpiredTokenException();

            var accountId = tokenData.getAccountId();
            if (!projectService.checkOwner(accountId, data.getProjectId())) return new ResponseEntity<>("", HttpStatus.FORBIDDEN);

            projectManagementService.deleteProject(data.getProjectId());
            return new ResponseEntity("ok", HttpStatus.OK);
        }
        catch (TokenNotFoundException e) {
            return new ResponseEntity(false, HttpStatus.OK);
        }
        catch (ExpiredTokenException ex) {
            System.out.println(ex.getMessage());
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
            return new ResponseEntity(false, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}
