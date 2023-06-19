package muzhevsky.org.core.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@ToString
public class ProjectCreationForm implements Serializable {
    @Size(min = 4, max = 50)
    private String name;
    private String shortDescription;
    private String description;
    private String address;
    private MultipartFile image;
}
