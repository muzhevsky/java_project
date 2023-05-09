package org.muzhevsky.projects.dtos;

import lombok.Getter;
import org.muzhevsky.resource.dtos.MyFile;

@Getter
public class ProjectForm {
    private String name;
    private String shortDescription;
    private String description;
    private MyFile[] files;
    private MyFile image;
    private String accessToken;
    private int folderId;
}
