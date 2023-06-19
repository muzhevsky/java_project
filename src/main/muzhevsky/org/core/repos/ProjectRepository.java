package muzhevsky.org.core.repos;


import muzhevsky.org.core.entities.Project;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.ws.rs.QueryParam;
import java.util.List;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Integer> {
    List<Project> findAllByOwnerId(String ownerId);
    List<Project> findAllByStatus(String status);

    @Modifying
    @Query("update projects set status=:status where id=:id")
    void updateStatus(@QueryParam("status") String status, @QueryParam("id") int id);
}
