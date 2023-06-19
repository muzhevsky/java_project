package muzhevsky.org.core.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@AllArgsConstructor
@NoArgsConstructor
@Table("estimate_sections")
@Getter
public class EstimateSection {
    @Id
    private Integer id;
    private int estimateId;
    private String name;
}
