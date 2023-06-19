package muzhevsky.org.core.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EstimateRequestDto {
    private String name;
    private String shortDescription;
    private List<EstimateSectionRequestDto> sections;
}
