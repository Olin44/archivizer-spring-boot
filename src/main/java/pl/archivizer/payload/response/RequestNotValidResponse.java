package pl.archivizer.payload.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;


@ResponseStatus(value = HttpStatus.BAD_REQUEST)
@Getter
public class RequestNotValidResponse{

    private final String message;
    private final List<ViolationResponse> violations;

    public RequestNotValidResponse(List<ViolationResponse> violations) {
        this.message = "Not valid fields";
        this.violations = violations;
    }

}