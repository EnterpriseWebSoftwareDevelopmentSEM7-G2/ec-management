//package org.group2.webapp.web.error;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpStatus;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseStatus;
//
//import javassist.NotFoundException;
//
//@ControllerAdvice
//@RequestMapping("/")
//public class GlobalDefaultExceptionHandler {
//
//    private static Logger logger = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);
//
//    @GetMapping("/error")
//    public String error(Exception e, Model model) {
//        model.addAttribute("errorMessage", e.getMessage());
//        return "error/error";
//    }
//
//    @ExceptionHandler(NotFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public String exceptionNotFound() {
//        return "error/404";
//    }
//
//    @GetMapping("/accessdenied")
//    public String exceptionAccessDenied() {
//        return "error/403";
//    }
//
//    @ExceptionHandler(value = Exception.class)
//    public String defaultErrorHandler(Exception e, Model model) throws
//            Exception {
//        model.addAttribute("errorMessage", e.getMessage());
//        return "error/error";
//    }
//
//}