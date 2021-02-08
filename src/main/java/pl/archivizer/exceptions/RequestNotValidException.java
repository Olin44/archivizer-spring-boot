package pl.archivizer.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.archivizer.models.BasicEntity;

import java.util.List;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
@Getter
public class RequestNotValidException extends RuntimeException{

    private final List<String> violations;

    public RequestNotValidException(List<String> violations) {
        super("Not valid fields");
        this.violations = violations;
    }

}
