package org.muzhevsky.platform.controllers;

import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProjectController {

    @GetMapping("/myprojects")
    public String someShit(){
        return "myProjects";
    }

    @GetMapping("/api/projects")
    public ResponseEntity<String> getProjectsOfUser(KeycloakAuthenticationToken authenticationToken){
        return new ResponseEntity<>("asd", HttpStatus.OK);
    }

    @GetMapping("/project/{id}")
    public String projectPage(){
        return "project";
    }

    @GetMapping("/api/project/{id}")
    public ResponseEntity<String> getProjectData(){
        return new ResponseEntity<>("asd", HttpStatus.OK);
    }
}