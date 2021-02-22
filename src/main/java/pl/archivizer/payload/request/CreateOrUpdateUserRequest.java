package pl.archivizer.payload.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import pl.archivizer.models.Role;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Setter
@Getter
@RequiredArgsConstructor
public class CreateOrUpdateUserRequest extends BasicRequest {
    @NotNull @NotEmpty
    private final String login;
    @NotNull @NotEmpty @Email
    private final String email;
    @NotNull
    private final boolean isActive;
    @NotNull @NotEmpty
    private final String name;
    @NotNull @NotEmpty
    private final String surname;
    @NotNull
    private final String pesel;
    @NotNull
    private final LocalDateTime creationDate;
    @NotNull
    private final LocalDateTime editDate;
    @NotNull
    private final Set<Role> roles;
}
