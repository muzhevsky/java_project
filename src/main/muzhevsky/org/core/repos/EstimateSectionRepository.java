package muzhevsky.org.core.repos;

import muzhevsky.org.core.entities.EstimateSection;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EstimateSectionRepository extends CrudRepository<EstimateSection, Integer> {
    List<EstimateSection> findAllByEstimateId(int estimateId);
}
