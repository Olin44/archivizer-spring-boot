package pl.archivizer.payload.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.archivizer.payload.response.simple.BasicResponse;


@Getter
@Setter
public class UserNameSurnameWithId extends BasicResponse {
    private String creator;
    @Builder
    public UserNameSurnameWithId(Long id, String creator) {
        this.id = id;
        this.creator = creator;
    }

    public UserNameSurnameWithId() {
    }
}
