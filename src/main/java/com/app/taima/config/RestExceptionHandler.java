package com.app.taima.config;

import com.app.taima.enums.ResponseType;
import com.app.taima.exception.AlreadyExistsException;
import com.app.taima.utils.GenericResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AlreadyExistsException.class)
    protected ResponseEntity<GenericResponse<Object>> handleEntityNotFound(AlreadyExistsException ex) {
        GenericResponse<Object> response = new GenericResponse<>(ResponseType.ERROR).code(ex.getMessage() + "AlreadyExist");
        return new ResponseEntity<>(response, BAD_REQUEST);
    }
}
