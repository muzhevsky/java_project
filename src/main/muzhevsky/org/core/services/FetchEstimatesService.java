package muzhevsky.org.core.services;

import muzhevsky.org.auth.services.AuthorizationService;
import muzhevsky.org.core.dtos.EstimateResponseDto;
import muzhevsky.org.core.dtos.EstimateSectionResponseDto;
import muzhevsky.org.core.dtos.EstimateSimpleDto;
import muzhevsky.org.core.entities.Estimate;
import muzhevsky.org.core.entities.EstimateSection;
import muzhevsky.org.core.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FetchEstimatesService {
    @Autowired
    EstimateRepository estimateRepository;
    @Autowired
    EstimateSectionRepository estimateSectionRepository;
    @Autowired
    EstimateLabourUnitsRepository estimateLabourUnitsRepository;
    @Autowired
    EstimateMaterialUnitRepository estimateMaterialUnitsRepository;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    AuthorizationService authorizationService;
    public EstimateResponseDto getEstimate(int id){
        var estimateSectionIds = new ArrayList<Integer>(estimateSectionRepository.findAllByEstimateId(id)
                .stream()
                .map(EstimateSection::getId)
                .toList());

        var estimateSectionDtos = new ArrayList<EstimateSectionResponseDto>();
        for(var sectionId : estimateSectionIds){
            var section = estimateSectionRepository.findById(sectionId).orElseThrow(NotFoundException::new);
            var labourList = estimateLabourUnitsRepository.findAllByEstimateSectionId(sectionId);
            var materialList = estimateMaterialUnitsRepository.findAllByEstimateSectionId(sectionId);

            var newEstimateSection = new EstimateSectionResponseDto(sectionId, section.getName(), labourList, materialList);
            estimateSectionDtos.add(newEstimateSection);
        }

        var estimate = estimateRepository.findById(id).orElseThrow(NotFoundException::new);
        var company = authorizationService.getAccountDtoById(estimate.getCompanyId());

        return new EstimateResponseDto(id, company.getAttributes().get("companyName").get(0),
                estimate.getProjectOfficialName(),
                estimate.getStatus(),
                estimate.getCreationDate(),
                estimateSectionDtos);
    }

    public List<EstimateResponseDto> getAllEstimatesAllData(int projectId){
        var estimateIds = estimateRepository.findAllByProjectId(projectId)
                .stream()
                .map(Estimate::getId)
                .toList();
        var list = new ArrayList<EstimateResponseDto>();
        for (var id : estimateIds){
            list.add(getEstimate(id));
        }

        return list;
    }

    public List<EstimateSimpleDto> getEstimatesSimpleDataByProject(int projectId){
        var estimates = estimateRepository.findAllByProjectId(projectId);
        var companyIds = estimates
                .stream()
                .map(Estimate::getCompanyId)
                .distinct()
                .toList();
        var estimateDtos = new ArrayList<EstimateSimpleDto>();
        for (var companyId: companyIds)
            estimateDtos.addAll(getEstimatesSimpleDataByProjectAndCompany(projectId, companyId));

        return estimateDtos;
    }

    public List<EstimateSimpleDto> getEstimatesSimpleDataByProjectAndCompany(int projectId, String companyId){
        var estimates = estimateRepository.findAllByProjectId(projectId)
                .stream()
                .filter(estimate -> estimate.getCompanyId().equals(companyId))
                .toList();
        if (estimates.size() == 0) return List.of();
        var company = authorizationService.getAccountDtoById(companyId);
        var project = projectRepository.findById(projectId).orElseThrow(NotFoundException::new);
        var estimateDtos = estimates.stream().map(estimate -> new EstimateSimpleDto(estimate.getId(),
                        company.getAttributes().get("companyName").get(0),
                        project.getName(),
                        estimate.getStatus(),
                        estimate.getShortDescription(),
                        estimate.getCreationDate()))
                .toList();

        return estimateDtos;
    }
}