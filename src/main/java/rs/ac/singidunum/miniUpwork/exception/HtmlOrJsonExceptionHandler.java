package rs.ac.singidunum.miniUpwork.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import rs.ac.singidunum.miniUpwork.model.ErrorResponse;

@ControllerAdvice()
@Order(2)
public class HtmlOrJsonExceptionHandler {

    @ExceptionHandler({
            BusinessException.class,
            ResourceNotFoundException.class,
            Exception.class
    })
    public Object handle(Exception ex,
                          HttpServletRequest request,
                          Model model) {

        String accept = request.getHeader("Accept");
        boolean wantsHtml =
                accept == null
                        || accept.contains("text/html")
                        || accept.contains("*/*");

        if (wantsHtml) {
            model.addAttribute(
                    "errorMessage",
                    ex.getMessage());
            return "error";
        }

        int status;
        if (ex instanceof ResourceNotFoundException) {
            status = HttpStatus.NOT_FOUND.value();
        } else if (ex instanceof BusinessException) {
            status = HttpStatus.BAD_REQUEST.value();
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        }

        return ResponseEntity
                .status(status)
                .body(new ErrorResponse(
                        ex.getMessage(),
                        status));
    }
}

