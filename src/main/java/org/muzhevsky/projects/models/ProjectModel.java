package org.muzhevsky.projects.models;

import lombok.Getter;
import lombok.ToString;
import org.muzhevsky.projects.dtos.ProjectForm;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;

@Table("projects")
@Getter
@ToString
public class ProjectModel {
    @Id
    private Integer id;
    private int accountId;
    private String imageFileName;
    private String name;
    private String description;
    private String shortDescription;
    private String revoked;
    private Timestamp creationDate;
    private int folderId;
    private String fileName;


    public void init(ProjectForm form, int accountId, String imageFileName, String fileName){
        this.name = form.getName();
        this.accountId = accountId;
        this.imageFileName = imageFileName;
        this.description = form.getDescription();
        this.shortDescription = form.getShortDescription();
        this.fileName = fileName;
        this.revoked = "n";
        this.folderId = form.getFolderId();
    }
}