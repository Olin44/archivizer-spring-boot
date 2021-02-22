package pl.archivizer.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
@Getter
public class CustomEntityNotFoundListException extends RuntimeException{
    private final List<CustomEntityNotFoundException> errors;

    public CustomEntityNotFoundListException(List<CustomEntityNotFoundException> errors) {
        super("Entities not found");
        this.errors= errors;
    }
}
