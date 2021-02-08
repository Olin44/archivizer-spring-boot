package pl.archivizer.payload.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import pl.archivizer.models.Role;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Builder
public class RolesResponse {
    List<Role> roles;

    public RolesResponse(List<Role> roles) {
        this.roles = roles;
    }
}
