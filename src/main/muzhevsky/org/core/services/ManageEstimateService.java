package muzhevsky.org.core.services;

import muzhevsky.org.core.dtos.EstimateRequestDto;
import muzhevsky.org.core.entities.Estimate;
import muzhevsky.org.core.entities.EstimateLabourUnit;
import muzhevsky.org.core.entities.EstimateMaterialUnit;
import muzhevsky.org.core.entities.EstimateSection;
import muzhevsky.org.core.enums.EstimateStatus;
import muzhevsky.org.core.repos.EstimateLabourUnitsRepository;
import muzhevsky.org.core.repos.EstimateMaterialUnitRepository;
import muzhevsky.org.core.repos.EstimateRepository;
import muzhevsky.org.core.repos.EstimateSectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManageEstimateService {

    @Autowired
    EstimateRepository estimateRepository;
    @Autowired
    EstimateMaterialUnitRepository estimateMaterialUnitRepository;
    @Autowired
    EstimateLabourUnitsRepository estimateLabourUnitsRepository;
    @Autowired
    EstimateSectionRepository estimateSectionRepository;

    public void createEstimate(EstimateRequestDto estimate, int projectId, String companyId){
        var estimateEntity = new Estimate(null, companyId, projectId,
                estimate.getShortDescription(), estimate.getName(), EstimateStatus.ONUSERCONSIDERMENT.get(), null);

        estimateRepository.save(estimateEntity);
        for (var section : estimate.getSections()){
            var sectionEntity = new EstimateSection(null, estimateEntity.getId(), section.getName());
            estimateSectionRepository.save(sectionEntity);
            for (var estimateUnit : section.getMaterialUnits()){
                var unit = new EstimateMaterialUnit(estimateUnit, sectionEntity.getId());
                estimateMaterialUnitRepository.save(unit);
            }
            for (var estimateUnit : section.getLabourUnits()){
                var unit = new EstimateLabourUnit(estimateUnit, sectionEntity.getId());
                estimateLabourUnitsRepository.save(unit);
            }
        }
    }

    public void acceptEstimate(int estimateId){
        estimateRepository.udpateStatus(EstimateStatus.ACCEPTED.get(), estimateId);
    }

    public void declineEstimate(int estimateId){
        System.out.println("declined");
        estimateRepository.udpateStatus(EstimateStatus.REJECTED.get(), estimateId);
    }
}
