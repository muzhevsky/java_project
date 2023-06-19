package muzhevsky.org.auth.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AccountDto {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private Map<String, List<String>> attributes;
    public AccountDto(UserRepresentation representation){
        this.username = representation.getUsername();
        this.email = representation.getEmail();
        this.firstName = representation.getFirstName();
        this.lastName = representation.getLastName();
        this.attributes = representation.getAttributes();
    }
}
