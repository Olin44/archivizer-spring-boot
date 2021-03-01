package pl.archivizer.payload.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserNameSurnameWithId {
    private final Long id;
    private final String nameAndSurname;

    public UserNameSurnameWithId(Long id, String nameAndSurname) {
        this.id = id;
        this.nameAndSurname = nameAndSurname;
    }
}
