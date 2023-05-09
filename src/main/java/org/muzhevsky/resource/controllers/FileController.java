package org.muzhevsky.resource.controllers;

import org.muzhevsky.resource.repos.CommonFileRepository;
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
    @Qualifier("commonFileRepository")
    CommonFileRepository commonFileRepository;
    @GetMapping("files/{filename}")
    public ResponseEntity<byte[]> getImage(@PathVariable String filename){
        try{
            var image = commonFileRepository.getFileByName(filename);
            return new ResponseEntity<>(image.getContent(), HttpStatus.OK);
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
