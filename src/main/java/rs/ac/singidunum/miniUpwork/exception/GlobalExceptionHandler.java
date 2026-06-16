package rs.ac.singidunum.miniUpwork.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.core.annotation.Order;

import rs.ac.singidunum.miniUpwork.model.ErrorResponse;

@RestControllerAdvice()
@Order(3)
public class GlobalExceptionHandler {

    @ExceptionHandler(
            ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse>
    handleNotFound(
            ResourceNotFoundException ex) {

    	ErrorResponse response =
                new ErrorResponse(
                        ex.getMessage(),
                        HttpStatus.NOT_FOUND.value());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(
            BusinessException.class)
    public ResponseEntity<ErrorResponse>
    handleBusiness(
            BusinessException ex) {

    	ErrorResponse response =
                new ErrorResponse(
                        ex.getMessage(),
                        HttpStatus.BAD_REQUEST.value());

        return ResponseEntity
                .badRequest()
                .body(response);
    }

    @ExceptionHandler(
            Exception.class)
    public ResponseEntity<ErrorResponse>
    handleGeneral(
            Exception ex) {

    	ErrorResponse response =
                new ErrorResponse(
                        ex.getMessage(),
                        HttpStatus.INTERNAL_SERVER_ERROR.value());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
}