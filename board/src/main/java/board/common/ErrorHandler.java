package board.common;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(Exception.class)
    public ModelAndView defaultExceptionHandler(HttpServletRequest request, Exception exception) {
        ModelAndView mv = new ModelAndView("/error/default");
        mv.addObject("request", request);
        mv.addObject("message", exception.getMessage());
        mv.addObject("stackTrace", exception.getStackTrace());
        return mv;
    }
}
