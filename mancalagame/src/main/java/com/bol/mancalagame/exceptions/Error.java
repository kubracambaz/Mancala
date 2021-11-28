package com.bol.mancalagame.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
public class Error {
    private HttpStatus status;
    private String errorCode;
    private List<String> message;
}
