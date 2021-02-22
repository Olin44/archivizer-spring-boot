package pl.archivizer.payload.response.errors;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.archivizer.payload.response.FileWithMetadataSmallResponse;
import pl.archivizer.payload.response.ViolationResponse;

import java.util.List;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
@Getter
public class ConstrainsViolationResponse {
    private final String message;
    private final List<FileWithMetadataSmallResponse> violations;

    public ConstrainsViolationResponse(String message, List<FileWithMetadataSmallResponse> violations) {
        this.message = message;
        this.violations = violations;
    }
}
