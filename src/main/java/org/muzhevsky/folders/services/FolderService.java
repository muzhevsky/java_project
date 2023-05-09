package org.muzhevsky.folders.services;

import org.muzhevsky.folders.dtos.FolderResponse;
import org.muzhevsky.folders.models.FolderModel;
import org.muzhevsky.folders.repos.FolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FolderService
{
    @Autowired
    FolderRepository folderRepository;
    public List<FolderModel> getFolders(int userId){
        return folderRepository.findAllByAccountId(userId);
    }

    public List<FolderResponse> getFolderResponse(int userId){
        var models = getFolders(userId);
        var res = new ArrayList<FolderResponse>();
        for (var item : models){
            res.add(new FolderResponse(item.getId(), item.getName()));
        }

        return res;
    }

    public List<FolderModel> addFolder(String name, int userId){
        var model = new FolderModel(userId, name);

        folderRepository.save(model);
        return folderRepository.findAllByAccountId(userId);
    }

    public void moveProject (int projectId, int destinationFolderId){
        folderRepository.deleteProjectFromFolder(projectId);
        folderRepository.insertProjectToFolder(destinationFolderId, projectId);
    }
}
