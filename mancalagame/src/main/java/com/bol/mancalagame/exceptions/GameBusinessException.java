package com.bol.mancalagame.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameBusinessException extends RuntimeException {
    private  String errMsgKey;
    private  String errorCode;

    public GameBusinessException(ErrorCode code) {
        super(code.getErrMsgKey());
        this.errMsgKey = code.getErrMsgKey();
        this.errorCode = code.getErrCode();
    }

    public GameBusinessException(String message) {
        super(message);
    }
}
