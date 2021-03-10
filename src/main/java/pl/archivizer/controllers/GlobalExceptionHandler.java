package pl.archivizer.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.archivizer.exceptions.ConstrainsViolationsException;
import pl.archivizer.exceptions.CustomEntityNotFoundException;
import pl.archivizer.exceptions.EntityNotFoundException;
import pl.archivizer.payload.response.RequestNotValidResponse;
import pl.archivizer.payload.response.ViolationResponse;
import pl.archivizer.payload.response.errors.ConstrainsViolationResponse;
import pl.archivizer.payload.response.errors.ErrorResponse;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({EntityNotFoundException.class, CustomEntityNotFoundException.class})
    public ResponseEntity<ErrorResponse> userNotFoundExceptionHandler(Exception ex, WebRequest request) {
        ErrorResponse errors = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .error(ex.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .build();
        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<RequestNotValidResponse> handleConstraintViolationExceptions(
            ConstraintViolationException ex) {
        List<ViolationResponse> violations = new ArrayList<>();
        RequestNotValidResponse requestNotValidResponse = null;
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            ViolationResponse violationResponse = ViolationResponse.builder()
                    .invalidField(violation.getPropertyPath().toString())
                    .invalidValue(violation.getInvalidValue().toString())
                    .validationMessage(violation.getMessage())
                    .build();
            violations.add(violationResponse);
            requestNotValidResponse = new RequestNotValidResponse(violations);
        }
        return new ResponseEntity<>(requestNotValidResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IOException.class)
    public final ResponseEntity<IOException> handleReadFileError(
            IOException ex) {
        return new ResponseEntity<>(ex, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(ConstrainsViolationsException.class)
        public final ResponseEntity<ConstrainsViolationResponse> handleConstrainsViolationExceptions(ConstrainsViolationsException ex) {
        ConstrainsViolationResponse response = new ConstrainsViolationResponse(ex.getMessage(), ex.getViolationsList());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}