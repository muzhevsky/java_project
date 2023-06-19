package muzhevsky.org.core.repos;

import muzhevsky.org.core.entities.EstimateLabourUnit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstimateLabourUnitsRepository extends CrudRepository<EstimateLabourUnit, Integer> {
    List<EstimateLabourUnit> findAllByEstimateSectionId(int estimateSectionId);
}
