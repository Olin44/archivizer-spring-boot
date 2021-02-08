package pl.archivizer.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateSuccessResponse {
    private final String message;
    public UpdateSuccessResponse() {
        this.message = "Successfully created!";
    }
}
