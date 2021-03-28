package com.diploma.maksimov.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j //как JPA, только для логирования
@ControllerAdvice
public class MyExHandler extends ResponseEntityExceptionHandler {
    //помогает серверу не падать
    @ExceptionHandler(value = {Throwable.class})
    protected ResponseEntity<Object> handleConflict(Throwable ex, WebRequest request) {
        return handleExceptionInternal(new RuntimeException(), ex.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    //интегрирование нашего исключения в handler
    @ExceptionHandler(value = {MyException.class})
    protected ResponseEntity<Object> handleMyEx(MyException ex, WebRequest request) {
        //варианты логирования:
        log.error("Aaaa!", ex);
        log.error("Trouble with {}", ex.getMessage(),ex);
        return handleExceptionInternal(new RuntimeException(), ex.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

}
