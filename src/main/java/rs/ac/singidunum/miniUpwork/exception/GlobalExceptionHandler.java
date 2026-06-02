package rs.ac.singidunum.miniUpwork.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import rs.ac.singidunum.miniUpwork.dto.ErrorResponseDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(
            ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO>
    handleNotFound(
            ResourceNotFoundException ex) {

    	ErrorResponseDTO response =
                new ErrorResponseDTO(
                        ex.getMessage(),
                        HttpStatus.NOT_FOUND.value());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(
            BusinessException.class)
    public ResponseEntity<ErrorResponseDTO>
    handleBusiness(
            BusinessException ex) {

    	ErrorResponseDTO response =
                new ErrorResponseDTO(
                        ex.getMessage(),
                        HttpStatus.BAD_REQUEST.value());

        return ResponseEntity
                .badRequest()
                .body(response);
    }

    @ExceptionHandler(
            Exception.class)
    public ResponseEntity<ErrorResponseDTO>
    handleGeneral(
            Exception ex) {

    	ErrorResponseDTO response =
                new ErrorResponseDTO(
                        ex.getMessage(),
                        HttpStatus.INTERNAL_SERVER_ERROR.value());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
}