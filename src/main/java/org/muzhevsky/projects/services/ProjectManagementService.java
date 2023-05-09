package org.muzhevsky.projects.services;

import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.muzhevsky.projects.dtos.ProjectForm;
import org.muzhevsky.projects.models.ProjectModel;
import org.muzhevsky.projects.repos.ProjectRepository;
import org.muzhevsky.resources.repos.ImageFileRepository;
import org.muzhevsky.resources.repos.CommonFileRepository;
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
    @Qualifier("commonFileRepository")
    CommonFileRepository commonFileRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    Gson gson;

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
            String imageFileName;

            if (image.getContent().length > 1)
                imageFileName = imageRepository.save(image);
            else
                imageFileName = "default.jpg";

            var projectModel = new ProjectModel();
            var fileNames = Arrays.stream(form.getFiles()).map(file->commonFileRepository.save(file));

            var zipName = commonFileRepository.zip(fileNames.toList());

            projectModel.init(form, userId, imageFileName, zipName);

            projectRepository.createProject(projectModel.getAccountId(), projectModel.getName(),
                    projectModel.getDescription(), projectModel.getShortDescription(), imageFileName,
                    projectModel.getFileName(), projectModel.getFolderId());
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
