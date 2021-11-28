package com.bol.mancalagame.exceptions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ExceptionHandlerControllerAdviceTests {

    @InjectMocks
    ExceptionHandlerControllerAdvice controllerAdvice;

    @Test
    public void should_ThrowGameBusinessException(){
        List<String> message = new ArrayList<>();
        Error expectedError = new Error();
        expectedError.setStatus(HttpStatus.BAD_REQUEST);
        expectedError.setErrorCode(ErrorCode.PIT_AND_STONE_EXCEPTION.getErrCode());
        expectedError.setMessage(message);
        GameBusinessException businessException = new GameBusinessException(ErrorCode.PIT_AND_STONE_EXCEPTION);
        ResponseEntity<Error> error=controllerAdvice.handleGameBusinessException(businessException);
        assertEquals(expectedError.getStatus(),error.getBody().getStatus());
        assertEquals(expectedError.getErrorCode(),error.getBody().getErrorCode());
    }
}
