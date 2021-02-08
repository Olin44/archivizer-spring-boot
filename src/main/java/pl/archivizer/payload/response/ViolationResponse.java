package pl.archivizer.payload.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ViolationResponse {
    private final String invalidField;
    private final String validationMessage;
    private final String invalidValue;

    public ViolationResponse(String invalidField, String validationMessage, String invalidValue) {
        this.invalidField = invalidField;
        this.validationMessage = validationMessage;
        this.invalidValue = invalidValue;
    }
}
