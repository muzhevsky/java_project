package org.muzhevsky.folders.repos;

import org.muzhevsky.folders.models.FolderModel;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FolderRepository extends CrudRepository<FolderModel, Integer> {
    List<FolderModel> findAllByAccountId(int userId);

    @Modifying
    @Query("delete from projects_and_folders where project_id=:projectId")
    void deleteProjectFromFolder(@Param("projectId") int projectId);

    @Modifying
    @Query("insert into projects_and_folders(folder_id, project_id) values(:folderId, :projectId)")
    void insertProjectToFolder(@Param("folderId") int folderId, @Param("projectId") int projectId);
}
