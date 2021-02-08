package pl.archivizer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class EntityAlreadyExistException extends RuntimeException{

    public EntityAlreadyExistException(String entityType, String fieldType) {
        super("Conflict," + entityType + " with that " + fieldType + " already exist!");
    }

    public EntityAlreadyExistException() {
        super("Conflict! Entity already exist!");
    }
}
