package org.muzhevsky.folders.models;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("folders")
@Getter
@ToString
public class FolderModel {
    @Id
    private int id;
    private int accountId;
    private String name;

    public FolderModel(int accountId, String name){
        this.accountId = accountId;
        this.name = name;
    }
}
