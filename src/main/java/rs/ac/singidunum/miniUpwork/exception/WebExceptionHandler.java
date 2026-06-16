package rs.ac.singidunum.miniUpwork.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.core.annotation.Order;

@Order(1)
@ControllerAdvice()
public class WebExceptionHandler {

    @ExceptionHandler({
            BusinessException.class,
            ResourceNotFoundException.class,
            Exception.class
    })
    public String handleWebException(
            Exception ex,
            Model model) {

        model.addAttribute(
                "errorMessage",
                ex.getMessage());

        return "error";
    }
}
