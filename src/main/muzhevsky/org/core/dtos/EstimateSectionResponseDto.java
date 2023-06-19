package muzhevsky.org.core.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import muzhevsky.org.core.entities.EstimateLabourUnit;
import muzhevsky.org.core.entities.EstimateMaterialUnit;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EstimateSectionResponseDto {
    private int id;
    private String name;
    private List<EstimateLabourUnit> estimateLabourUnits;
    private List<EstimateMaterialUnit> estimateMaterialUnits;
}