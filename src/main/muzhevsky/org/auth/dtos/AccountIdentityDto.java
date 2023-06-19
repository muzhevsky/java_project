package muzhevsky.org.auth.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AccountIdentityDto {
    private String id;
    private String username;
    private String email;
}
