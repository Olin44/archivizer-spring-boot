package pl.archivizer.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeletionSuccessResponse {
    String message;

    public DeletionSuccessResponse() {
        this.message = "Successfully delete";
    }
}
