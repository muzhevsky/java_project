package muzhevsky.org.core.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
public class EstimateUnitDto {
    private String name;
    private String unit;
    private float amount;
    private float price;
}
