package muzhevsky.org.core.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import muzhevsky.org.core.dtos.EstimateUnitDto;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table("estimate_material_units")
public class EstimateMaterialUnit{
    @Id
    private int id;
    private int estimateSectionId;
    private String name;
    private String unit;
    private float amount;
    private float price;

    public EstimateMaterialUnit(EstimateUnitDto unitDto, int estimateSectionId){
        this.name = unitDto.getName();
        this.unit = unitDto.getUnit();
        this.amount = unitDto.getAmount();
        this.price = unitDto.getPrice();
        this.estimateSectionId = estimateSectionId;
    }
}
