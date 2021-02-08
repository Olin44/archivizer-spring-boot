package pl.archivizer.payload.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import pl.archivizer.models.Role;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
@Builder
public class UserDetailsDataResponse {
    private final Long id;
    private final String login;
    private final String email;
    private final boolean isActive;
    private final String name;
    private final String surname;
    private final String pesel;
    private final LocalDateTime creationDate;
    private final LocalDateTime editDate;
    private final Set<Role> roles;
}
