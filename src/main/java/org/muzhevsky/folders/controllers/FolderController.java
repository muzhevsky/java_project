package org.muzhevsky.folders.controllers;

import org.muzhevsky.authorization.exceptions.ExpiredTokenException;
import org.muzhevsky.authorization.services.AuthorizationService;
import org.muzhevsky.folders.dtos.MoveProjectsToFolderForm;
import org.muzhevsky.folders.services.FolderService;
import org.muzhevsky.folders.dtos.FolderForm;
import org.muzhevsky.folders.dtos.FolderResponse;
import org.muzhevsky.folders.models.FolderModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FolderController {

    @Autowired
    FolderService folderService;

    @Autowired
    @Qualifier("authorizationService")
    AuthorizationService authorizationService;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/home/user/createfolder")
    public ResponseEntity<List<FolderModel>> createfolder(@RequestBody FolderForm form){
        try {
            var tokenData = authorizationService.getTokenData(form.getAccessToken());
            if (tokenData.isExpired()) throw new ExpiredTokenException();

            var accountId = tokenData.getAccountId();
            return new ResponseEntity<>(folderService.addFolder(form.getName(), accountId), HttpStatus.OK);
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
    @GetMapping("/home/user/fetchfolders")
    public ResponseEntity<List<FolderResponse>> fetchfolders(@RequestHeader("accessToken") String token){
        try {
            var userId = authorizationService.getTokenData(token).getAccountId();
            return new ResponseEntity<>(folderService.getFolderResponse(userId), HttpStatus.OK);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/home/user/moveProjects")
    public void addProjectToFolder(@RequestBody MoveProjectsToFolderForm form){

    }
}
