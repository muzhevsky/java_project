package org.muzhevsky.projects.services;

import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.muzhevsky.projects.dtos.ProjectForm;
import org.muzhevsky.projects.models.ProjectModel;
import org.muzhevsky.projects.repos.ProjectRepository;
import org.muzhevsky.resources.repos.ProjectFilesRepository;
import org.muzhevsky.resources.repos.ImageFileRepository;
import org.muzhevsky.utils.Zipper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ProjectManagementService {
    @Autowired
    @Qualifier("imageFileRepository")
    ImageFileRepository imageRepository;

    @Autowired
    @Qualifier("projectFilesRepository")
    ProjectFilesRepository projectFilesRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    Gson gson;

    @Autowired
    Zipper zipper;

    public List<ProjectModel> getProjects(int folderId, int userId){
        if(folderId == -1){
            var all = projectRepository.findAllByAccountIdAndRevoked(userId, 'n');
            return all;
        }
        else if (folderId == -2){
            var revoked = projectRepository.findAllByAccountIdAndRevoked(userId, 'y');
            return revoked;
        }
        var projects  = projectRepository.findAllByFolderIdAndRevoked(folderId, 'n');

        return projects;
    }

    @SneakyThrows
    public void createProject(ProjectForm form, int userId){
        try {
            var image = form.getImage();
            var imageFileName = imageRepository.save(image);
            var projectModel = new ProjectModel();

            projectModel.init(form, userId, imageFileName);

            var entity = projectRepository.save(projectModel);
            System.out.println(entity.getId());
//            var id = projectRepository.createProject(projectModel.getAccountId(), projectModel.getName(),
//                    projectModel.getDescription(), projectModel.getShortDescription(), projectModel.getFolderId(), imageFileName);
//            var id = projectRepository.getLastInsert();
//            var filePaths = Arrays.stream(form.getFiles()).map(file -> projectFilesRepository.save(id, file));
//            var zipName = zipper.zip(id, filePaths.toList());
//
//            projectRepository.updateFileNames(id, zipName);
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void deleteProject(int projectId){
        projectRepository.markProjectRevoked(projectId);
    }
    public void moveProject(int projectId, int folderId) {projectRepository.moveProject(projectId, folderId);}
}
