package muzhevsky.org.core.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("projects_and_companies")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProjectAndCompany {
    private int projectId;
    private String companyId;
}
