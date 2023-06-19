package muzhevsky.org.core.dtos;

import lombok.*;
import muzhevsky.org.core.entities.EstimateLabourUnit;
import muzhevsky.org.core.entities.EstimateMaterialUnit;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EstimateSectionRequestDto {
    private String name;
    private EstimateUnitDto[] labourUnits;
    private EstimateUnitDto[] materialUnits;
}
