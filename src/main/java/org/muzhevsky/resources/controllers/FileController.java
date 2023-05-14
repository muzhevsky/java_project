package org.muzhevsky.resources.controllers;

import org.muzhevsky.resources.repos.ProjectFilesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileController{
    @Autowired
    @Qualifier("projectFilesRepository")
    ProjectFilesRepository projectFilesRepository;
    @GetMapping("files/{projectId}")
    public ResponseEntity<byte[]> getFile(@PathVariable int projectId){
        try{
            var file = projectFilesRepository.getFileById(projectId);
            return new ResponseEntity<>(file.getContent(), HttpStatus.OK);
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
