package com.bol.mancalagame.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @ExceptionHandler(GameBusinessException.class)
    public ResponseEntity<Error> handleGameBusinessException(GameBusinessException businessException) {
        Error errorObject = new Error();
        List<String> message = new ArrayList<>();
        message.add(businessException.getErrMsgKey());
        errorObject.setErrorCode(businessException.getErrorCode());
        errorObject.setMessage(message);
        errorObject.setStatus(HttpStatus.BAD_REQUEST);
        logger.error(errorObject.getErrorCode() + ": " +errorObject.getMessage());
        return new ResponseEntity<>(errorObject, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        List<String> errorList = exception
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getDefaultMessage())
                .collect(Collectors.toList());
        Error error = new Error();
        error.setStatus(HttpStatus.BAD_REQUEST);
        error.setErrorCode(HttpStatus.BAD_REQUEST.toString());
        error.setMessage(errorList);
        logger.error(error.getErrorCode() + ": " +error.getMessage());
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }
}
