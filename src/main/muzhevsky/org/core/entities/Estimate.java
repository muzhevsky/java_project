package muzhevsky.org.core.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import muzhevsky.org.core.dtos.EstimateRequestDto;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;


@Table(name = "estimates")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Estimate {
    @Id
    private Integer id;
    private String companyId;
    private int projectId;
    private String shortDescription;
    private String projectOfficialName;
    private String status;
    private Timestamp creationDate;
}