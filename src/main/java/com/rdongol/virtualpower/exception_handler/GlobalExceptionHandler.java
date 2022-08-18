package com.rdongol.virtualpower.exception_handler;

import com.rdongol.virtualpower.exception_handler.exception.UnProcessableEntityException;
import com.rdongol.virtualpower.model.response.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    public static final Logger LOGGER = Logger.getLogger("GlobalExceptionHandler");


    @Override
    public ResponseEntity<Object> handleExceptionInternal(
            Exception ex,
            Object body,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        LOGGER.log(Level.SEVERE, "Internal exception", ex);
        return buildErrorResponse(ex, status);
    }

    @ExceptionHandler(UnProcessableEntityException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<Object> handleUnProcessableEntityException(UnProcessableEntityException unProcessableEntityException, WebRequest request) {
        LOGGER.log(Level.WARNING, "Un processable entity" + unProcessableEntityException.getMessage(), unProcessableEntityException);
        return buildErrorResponse(unProcessableEntityException, HttpStatus.UNPROCESSABLE_ENTITY, unProcessableEntityException.getErrors());
    }

    private ResponseEntity<Object> buildErrorResponse(Exception exception,
                                                      HttpStatus httpStatus) {
        ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(), exception.getMessage());

        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

    private ResponseEntity<Object> buildErrorResponse(Exception exception,
                                                      HttpStatus httpStatus,
                                                      List<String> errors) {
        ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(), exception.getMessage(), errors);
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

}
