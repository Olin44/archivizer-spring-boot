package pl.archivizer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CustomEntityNotFoundException extends RuntimeException {
    public CustomEntityNotFoundException(Long id, String objectType) {
        super("Object id of type " + objectType +" not found : " + id);
    }
}
