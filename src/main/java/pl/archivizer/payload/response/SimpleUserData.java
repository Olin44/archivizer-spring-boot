package pl.archivizer.payload.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class SimpleUserData {
    private final Long id;
    private final String login;
    private final String email;
    private final boolean isActive;

    public SimpleUserData(Long id, String login, String email, boolean isActive) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.isActive = isActive;
    }

}
