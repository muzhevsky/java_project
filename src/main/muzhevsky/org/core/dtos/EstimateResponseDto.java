package muzhevsky.org.core.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EstimateResponseDto {
    private int id;
    private String companyName;
    private String projectName;
    private String status;
    private Timestamp creationDate;
    private List<EstimateSectionResponseDto> estimateSectionList;
}