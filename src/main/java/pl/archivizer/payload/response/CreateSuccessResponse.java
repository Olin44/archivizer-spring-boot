package pl.archivizer.payload.response;

import lombok.Getter;
import lombok.Setter;
import pl.archivizer.payload.response.simple.BasicResponse;

@Getter
@Setter
public class CreateSuccessResponse {
    String message;

    public CreateSuccessResponse(String entityType) {
        this.message = "Successfully created " + entityType;
    }

    public CreateSuccessResponse() {
        this.message = "Successfully created";
    }
}
