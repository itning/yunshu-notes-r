package top.itning.yunshunotesr.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletRequest;

/**
 * @author itning
 */
@EnableWebMvc
@ControllerAdvice
public class ExceptionResolver {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionResolver.class);

    @Bean
    DispatcherServlet dispatcherServlet() {
        DispatcherServlet ds = new DispatcherServlet();
        ds.setThrowExceptionIfNoHandlerFound(true);
        return ds;
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public String defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        System.out.println(e.getClass().getSimpleName());
        return e.getMessage();
    }
}
