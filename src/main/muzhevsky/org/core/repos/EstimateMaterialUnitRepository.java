package muzhevsky.org.core.repos;

import muzhevsky.org.core.entities.EstimateMaterialUnit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstimateMaterialUnitRepository extends CrudRepository<EstimateMaterialUnit, Integer> {
    List<EstimateMaterialUnit> findAllByEstimateSectionId(int estimateSectionId);
}
