package org.muzhevsky.projects.repos;

import org.muzhevsky.projects.models.ProjectModel;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends CrudRepository<ProjectModel, Integer> {

    List<ProjectModel> findAllByFolderId(int folderId);
    List<ProjectModel> findAllByFolderIdAndRevoked(int folderId, char revoked);

    List<ProjectModel> findAllByAccountIdAndRevoked(int accountId, char revoked);

    @Modifying
    @Query("update projects set folder_id=:folderId")
    void moveProject(Integer id, int folderId);

    @Modifying
    @Query("update projects set revoked='y' where id=:id")
    void markProjectRevoked(@Param("id") Integer id);

    @Modifying
    @Query("insert into projects(account_id, file_name, image_file_name, name, description, short_description, revoked, folder_id)" +
            "values (:accountId, :fileName, :image, :name, :description, :shortDescription, 'n', :folderId)")
    void createProject (@Param("accountId") Integer accountId, @Param("name") String name,
                        @Param("description") String description, @Param("shortDescription") String shortDescription,
                        @Param("image") String image, @Param("fileName") String fileName,
                        @Param("folderId") Integer folderId);
}
