package com.bol.mancalagame.exceptions;

import lombok.Getter;

@Getter
public enum ErrorCode {

    PIT_AND_STONE_EXCEPTION("ERR-001", "Exceeded the limit for the number of pits to start the game."),
    MOVE_EXCEPTION("ERR-002", "Looks Like Wrong Player or Empty Pit"),
    GAME_STATUS_EXCEPTION("ERR-003", "Game is over !");

    private String errCode;
    private String errMsgKey;

    private ErrorCode(final String errCode, final String errMsgKey) {
        this.errCode = errCode;
        this.errMsgKey = errMsgKey;
    }
}
