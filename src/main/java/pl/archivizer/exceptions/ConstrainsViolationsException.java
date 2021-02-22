package pl.archivizer.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.archivizer.payload.response.FileWithMetadataSmallResponse;

import java.util.List;
@Getter
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ConstrainsViolationsException extends RuntimeException  {
    private final List<FileWithMetadataSmallResponse> violationsList;

    public ConstrainsViolationsException(List<FileWithMetadataSmallResponse> violationsList) {
        super("Violations exception");
        this.violationsList = violationsList;
    }
}
