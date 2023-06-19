package muzhevsky.org.core.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EstimateSimpleDto {
    private int id;
    private String companyName;
    private String projectName;
    private String status;
    private String shortDescription;
    private Timestamp creationDate;
}
