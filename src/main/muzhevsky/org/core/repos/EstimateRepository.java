package muzhevsky.org.core.repos;

import muzhevsky.org.core.entities.Estimate;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import javax.ws.rs.QueryParam;
import java.util.List;

public interface EstimateRepository extends CrudRepository<Estimate, Integer> {
    List<Estimate> findAllByProjectId(int projectId);

    @Modifying
    @Query("update estimates set status=:status where id=:id")
    void udpateStatus(@QueryParam("status") String status,
                      @QueryParam("id") int id);
}
