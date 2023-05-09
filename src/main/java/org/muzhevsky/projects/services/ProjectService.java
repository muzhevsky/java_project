package org.muzhevsky.projects.services;

import lombok.SneakyThrows;
import org.muzhevsky.projects.exceptions.ProjectNotFoundException;
import org.muzhevsky.projects.models.ProjectModel;
import org.muzhevsky.projects.repos.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Service
public class ProjectService {
    @Autowired
    ProjectRepository projectRepository;

    @SneakyThrows
    public ProjectModel getProjects(int projectId) throws ProjectNotFoundException {
        return projectRepository.findById(projectId).orElseThrow(new Supplier<Throwable>() {
            @Override
            public Throwable get() {
                return new ProjectNotFoundException();
            }
        });
    }

    public Boolean checkOwner(int accountId, int projectId){
        var project = projectRepository.findById(projectId);
        if (project.isEmpty()) return false;

        return project.get().getAccountId() == accountId;
    }
}
