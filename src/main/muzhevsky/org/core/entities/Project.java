package muzhevsky.org.core.entities;

import lombok.*;
import muzhevsky.org.core.dtos.ProjectCreationForm;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Table(name = "projects")
@Data
@NoArgsConstructor
public class Project {
    @Id
    private Integer id;
    private String ownerId;
    @Size(min = 3, max = 50)
    private String name;
    @Size(max = 64)
    private String shortDescription;
    @Size(max = 2048)
    private String description;
    private Timestamp creationDate;
    @Size(max = 64)
    private String imageFileName;
    @Size(max = 256)
    private String address;
    private String status;

    public Project(ProjectCreationForm form) {
        this.name = form.getName();
        this.address = form.getAddress();
        this.shortDescription = form.getShortDescription();
        this.description = form.getDescription();
        this.status = "active";
    }
}
