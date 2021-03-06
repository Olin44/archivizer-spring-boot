package pl.archivizer.payload.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.archivizer.payload.response.simple.BasicResponse;


@Getter
@Setter
public class UserNameSurnameWithId extends BasicResponse {
    private String nameAndSurname;
    @Builder
    public UserNameSurnameWithId(Long id, String nameAndSurname) {
        this.id = id;
        this.nameAndSurname = nameAndSurname;
    }

    public UserNameSurnameWithId() {
    }
}
